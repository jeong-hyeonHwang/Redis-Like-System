package com.jhh.api.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeDto {
    Integer commentId;
    Integer likeCount;
    boolean isUserLiked;

    public CommentLikeDto(Integer commentId) {
        this.commentId = commentId;
    }
}
