package com.jhh.api.post.service.db_base;

import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;

public interface DBBaseLikeService {
    PostLikeDto likePost(Integer userId, Integer postId);

    CommentLikeDto likeComment(Integer userId, Integer postId, Integer commentId);

    Integer getPostLikeCount(Integer postId);

    Integer getCommentLikeCount(Integer postId, Integer commentId);
}
