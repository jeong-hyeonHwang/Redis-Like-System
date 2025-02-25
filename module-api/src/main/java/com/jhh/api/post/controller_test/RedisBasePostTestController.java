package com.jhh.api.post.controller_test;

import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.service.redis_base.RedisBaseLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis-base/test/posts")
@RequiredArgsConstructor
public class RedisBasePostTestController {

    private final RedisBaseLikeService redisBaseLikeService;

    @PostMapping("/{userId}/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@PathVariable Integer userId,
                                              @PathVariable Integer postId) {
        PostLikeDto dto = redisBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{userId}/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likeComment(@PathVariable Integer userId,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer commentId) {
        CommentLikeDto dto = redisBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
