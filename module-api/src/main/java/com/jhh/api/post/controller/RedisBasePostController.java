package com.jhh.api.post.controller;

import com.jhh.api.common.exception.NotFoundException;
import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.common.response.BaseResponseErrorStatus;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.service.redis_base.RedisBaseLikeService;
import com.jhh.core.domain.comment.service.CommentService;
import com.jhh.core.domain.post.service.PostService;
import com.jhh.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis-base/posts")
@RequiredArgsConstructor
public class RedisBasePostController {

    private final RedisBaseLikeService redisBaseLikeService;

    @PostMapping("/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Integer postId) {
        Integer userId = Integer.valueOf(jwt.getId());
        PostLikeDto dto = redisBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Integer postId,
                                                 @PathVariable Integer commentId) {
        Integer userId = Integer.valueOf(jwt.getId());
        CommentLikeDto dto = redisBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
