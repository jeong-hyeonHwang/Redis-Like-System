package com.jhh.api.post.service;

import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.core.domain.comment.Comment;
import com.jhh.core.domain.comment.repository.CommentRepository;
import com.jhh.core.domain.comment_like_count.CommentLikeCount;
import com.jhh.core.domain.comment_like_count.repository.CommentLikeCountRepository;
import com.jhh.core.domain.post_like_count.PostLikeCount;
import com.jhh.core.domain.post.Post;
import com.jhh.core.domain.user.User;
import com.jhh.core.domain.user_comment_like.UserCommentLike;
import com.jhh.core.domain.user_comment_like.repository.UserCommentLikeRepository;
import com.jhh.core.domain.user_post_like.UserPostLike;
import com.jhh.core.domain.post_like_count.repository.PostLikeCountRepository;
import com.jhh.core.domain.post.repository.PostRepository;
import com.jhh.core.domain.user.repository.UserRepository;
import com.jhh.core.domain.user_post_like.repository.UserPostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBBaseLikeServiceImpl implements DBBaseLikeService {

    private final UserPostLikeRepository userPostLikeRepository;
    private final PostLikeCountRepository postLikeCountRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final UserCommentLikeRepository userCommentLikeRepository;
    private final CommentLikeCountRepository commentLikeCountRepository;
    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public PostLikeDto likePost(Integer userId, Integer postId) {
        // 포스트와 사용자 엔티티를 먼저 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 사용자가 해당 포스트에 대해 좋아요한 기록이 있는지 확인
        Optional<UserPostLike> existingUserPostLike = userPostLikeRepository.findByUserIdAndPostId(userId, postId);

        // 포스트의 좋아요 카운트 엔티티를 조회. 없으면 새로 생성.
        PostLikeCount postLikeCount = postLikeCountRepository.findByPost(post)
                .orElse(PostLikeCount.builder().post(post).likeCount(0).build());

        boolean userLiked;
        if (existingUserPostLike.isPresent()) {
            // 이미 좋아요한 경우 -> 좋아요 취소: 좋아요 기록 삭제, 카운트 감소
            existingUserPostLike.ifPresent(userPostLikeRepository::delete);
            postLikeCount.setLikeCount(postLikeCount.getLikeCount() - 1);
            userLiked = false;
        } else {
            // 아직 좋아요하지 않은 경우 -> 좋아요 추가: 좋아요 기록 저장, 카운트 증가
            UserPostLike newUserPostLike = UserPostLike.builder()
                    .user(user)
                    .post(post)
                    .build();
            userPostLikeRepository.save(newUserPostLike);
            postLikeCount.setLikeCount(postLikeCount.getLikeCount() + 1);
            userLiked = true;
        }

        // 좋아요 카운트 저장 (존재하지 않았다면 새로 insert, 있다면 update)
        postLikeCountRepository.save(postLikeCount);

        // 결과 DTO 설정
        PostLikeDto dto = new PostLikeDto(postId);
        dto.setUserLiked(userLiked);
        dto.setLikeCount(postLikeCount.getLikeCount());
        return dto;
    }

    @Override
    @Transactional
    public CommentLikeDto likeComment(Integer userId, Integer postId, Integer commentId) {
        // 1. 댓글, 포스트, 사용자 존재 여부 확인
        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 2. 사용자가 해당 댓글에 좋아요한 기록이 있는지 확인
        Optional<UserCommentLike> existingCommentLike = userCommentLikeRepository.findByUserIdAndCommentId(userId, commentId);

        // 3. 댓글 좋아요 카운트 엔티티 조회 (없으면 새로 생성)
        CommentLikeCount commentLikeCount = commentLikeCountRepository.findByComment(comment)
                .orElse(CommentLikeCount.builder().comment(comment).likeCount(0).build());

        boolean userLiked;
        if (existingCommentLike.isPresent()) {
            // 이미 좋아요한 경우 -> 좋아요 취소: 좋아요 기록 삭제, 카운트 감소
            userCommentLikeRepository.delete(existingCommentLike.get());
            commentLikeCount.setLikeCount(commentLikeCount.getLikeCount() - 1);
            userLiked = false;
        } else {
            // 아직 좋아요하지 않은 경우 -> 좋아요 추가: 좋아요 기록 저장, 카운트 증가
            UserCommentLike newCommentLike = UserCommentLike.builder()
                    .user(user)
                    .comment(comment)
                    .build();
            userCommentLikeRepository.save(newCommentLike);
            commentLikeCount.setLikeCount(commentLikeCount.getLikeCount() + 1);
            userLiked = true;
        }

        // 4. 댓글 좋아요 카운트 저장 (insert 또는 update)
        commentLikeCountRepository.save(commentLikeCount);

        // 5. 결과 DTO 생성 및 반환
        CommentLikeDto dto = new CommentLikeDto(commentId);
        dto.setUserLiked(userLiked);
        dto.setLikeCount(commentLikeCount.getLikeCount());
        return dto;
    }

    @Override
    public Integer getPostLikeCount(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return postLikeCountRepository.findByPost(post)
                .map(PostLikeCount::getLikeCount)
                .orElse(0);
    }

    @Override
    public Integer getCommentLikeCount(Integer postId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        return commentLikeCountRepository.findByComment(comment)
                .map(CommentLikeCount::getLikeCount)
                .orElse(0);
    }
}
