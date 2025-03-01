package com.jhh.api.post.service.redis_base;

import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;

public interface RedisBaseLikeService {
    PostLikeDto likePost(Integer userId, Integer postId);

    CommentLikeDto likeComment(Integer userId, Integer postId, Integer commentId);

    Integer getPostLikeCount(Integer postId);

    Integer getCommentLikeCount(Integer postId, Integer commentId);

    boolean isUserLikedPost(Integer userId, Integer postId);
}
