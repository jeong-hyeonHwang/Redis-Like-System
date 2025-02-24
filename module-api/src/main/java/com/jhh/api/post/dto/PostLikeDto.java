package com.jhh.api.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDto {
    Integer postId;
    Integer likeCount;
    boolean isUserLiked;

    public PostLikeDto(int postId) {
        this.postId = postId;
    }
}
