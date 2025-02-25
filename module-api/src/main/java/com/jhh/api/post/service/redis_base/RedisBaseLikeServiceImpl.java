package com.jhh.api.post.service.redis_base;

import com.jhh.api.common.exception.NotFoundException;
import com.jhh.api.common.response.BaseResponseErrorStatus;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.LikeCountDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.dto.UserLikeDataDto;
import com.jhh.core.domain.comment.repository.CommentRepository;
import com.jhh.core.domain.post.repository.PostRepository;
import com.jhh.core.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisBaseLikeServiceImpl implements RedisBaseLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, UserLikeDataDto> userLikeHashOperations;
    private HashOperations<String, String, LikeCountDto> likeCountHashOperations;

    @Override
    public PostLikeDto likePost(Integer userId, Integer postId) {
        // 포스트와 사용자 엔티티를 먼저 조회
//        postRepository.findById(postId)
//                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_POST));
//        userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_USER));

        String postHash = getPostKey(postId);
        String postHashKey = getPostLikeCountHashKey();
        String userHash = getUserKey(userId);
        String userPostHashKey = getPostLikeUserHashKey(postId);

//        redisTemplate.multi();

        PostLikeDto dto = new PostLikeDto(postId);
        boolean isUserLiked = isUserLikedPost(userHash, userPostHashKey);
        if (isUserLiked) {
            dto.setLikeCount(decrementPostLikeCount(postHash, postHashKey));
        } else {
            dto.setLikeCount(incrementPostLikeCount(postHash, postHashKey));
        }
        dto.setUserLiked(setUserLikePost(userHash, userPostHashKey, !isUserLiked));

//        redisTemplate.exec();
        return dto;
    }

    @Override
    public CommentLikeDto likeComment(Integer userId, Integer postId, Integer commentId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_POST));
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_USER));
        commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_COMMENT));

        String postHash = getPostKey(postId);
        String commentHashKey = getCommentLikeCountHashKey(commentId);
        String userHash = getUserKey(userId);
        String userCommentHashKey = getCommentLikeHashKey(postId);

        redisTemplate.multi();

        CommentLikeDto dto = new CommentLikeDto(commentId);
        boolean isUserLikedComment = isUserLikedComment(userHash, userCommentHashKey);
        if (isUserLikedComment) {
            dto.setLikeCount(incrementCommentLikeCount(postHash, commentHashKey));
        } else {
            dto.setLikeCount(decrementCommentLikeCount(postHash, commentHashKey));
        }
        dto.setUserLiked(setUserLikeComment(userHash, userCommentHashKey, !isUserLikedComment));

        redisTemplate.exec();
        return dto;
    }

    @Override
    public Integer getPostLikeCount(Integer postId) {
        String postHash = getPostKey(postId);
        String postHashKey = getPostLikeCountHashKey();
        Object value =  likeCountHashOperations.get(postHash, postHashKey);
        if (value == null) {
            return 0;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof LikeCountDto) {
            return ((LikeCountDto) value).getLikeCount();
        } else {
            throw new IllegalStateException("Unexpected type stored in Redis for like count: " + value.getClass());
        }
    }

    @Override
    public Integer getCommentLikeCount(Integer postId, Integer commentId) {
        String postHash = getPostKey(postId);
        String commentHashKey = getCommentLikeCountHashKey(commentId);
        LikeCountDto dto =  likeCountHashOperations.get(postHash, commentHashKey);
        return dto.getLikeCount();
    }

    @Override
    public boolean isUserLikedPost(Integer userId, Integer postId) {
        String userHash = getUserKey(userId);
        String userPostHashKey = getPostLikeUserHashKey(postId);

        return isUserLikedPost(userHash, userPostHashKey);
    }

    private boolean isUserLikedPost(String hash, String hashKey) {
        UserLikeDataDto dto = userLikeHashOperations.get(hash, hashKey);
        if (dto == null) {
            dto = new UserLikeDataDto(false);
        }
        return dto.isLiked();
    }

    private Integer incrementPostLikeCount(String hash, String hashKey) {
        return Math.toIntExact(likeCountHashOperations.increment(hash, hashKey, 1));
    }

    private Integer decrementPostLikeCount(String hash, String hashKey) {
        return Math.toIntExact(likeCountHashOperations.increment(hash, hashKey, -1));
    }

    private boolean setUserLikePost(String hash, String hashKey, boolean isLiked) {
        UserLikeDataDto dto = new UserLikeDataDto(isLiked);
        userLikeHashOperations.put(hash, hashKey, dto);
        return dto.isLiked();
    }

    public boolean isUserLikedComment(String hash, String hashKey) {
        UserLikeDataDto dto = userLikeHashOperations.get(hash, hashKey);
        if (dto == null) {
            dto = new UserLikeDataDto(false);
        }
        return dto.isLiked();
    }

    private Integer incrementCommentLikeCount(String hash, String hashKey) {
        likeCountHashOperations.increment(hash, hashKey, 1);
        return 0;
    }

    private Integer decrementCommentLikeCount(String hash, String hashKey) {
        likeCountHashOperations.increment(hash, hashKey, -1);
        return 0;
    }

    private boolean setUserLikeComment(String hash, String hashKey, boolean isLiked) {
        UserLikeDataDto dto = new UserLikeDataDto(isLiked);
        userLikeHashOperations.put(hash, hashKey, dto);
        return dto.isLiked();
    }

    private String getPostKey(Integer postId) {
        return "post:" + postId;
    }

    private String getPostLikeCountHashKey() {
        return "like:count";
    }

    private String getCommentLikeCountHashKey(Integer commentId) {
        return "like:count:comment:" + commentId;
    }

    private String getUserKey(Integer userId) {
        return "user" + ":" + userId;
    }

    private String getPostLikeUserHashKey(Integer postId) {
        return "post:" + postId;
    }

    private String getCommentLikeHashKey(Integer commentId) {
        return "comment:" + commentId;
    }
}
