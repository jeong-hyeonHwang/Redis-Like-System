package com.jhh.api.post.controller;

import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.api.post.service.redis_base.RedisBaseLikeService;
import com.jhh.api.post.service.redis_base.RedisBasePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis-base/posts")
@RequiredArgsConstructor
public class RedisBasePostController {

    private final RedisBasePostService redisBasePostService;
    private final RedisBaseLikeService redisBaseLikeService;

    @GetMapping("/{postId}")
    public BaseResponse<PostResponseDto> getPost(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Integer postId) {
        Integer userId = Integer.valueOf(jwt.getId());
        PostResponseDto dto = redisBasePostService.getPost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Integer postId) {
        Integer userId = Integer.valueOf(jwt.getId());
        PostLikeDto dto = redisBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likeComment(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Integer postId,
                                                 @PathVariable Integer commentId) {
        Integer userId = Integer.valueOf(jwt.getId());
        CommentLikeDto dto = redisBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
