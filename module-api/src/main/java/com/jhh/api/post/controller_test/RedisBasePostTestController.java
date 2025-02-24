package com.jhh.api.post.controller_test;

import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.service.RedisBaseLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis-base/test/post")
@RequiredArgsConstructor
public class RedisBasePostTestController {

    private final RedisBaseLikeService redisBaseLikeService;

    @PostMapping("/{userId}/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@PathVariable Integer userId,
                                              @PathVariable Integer postId) {
        // 서비스 내부 검증 없이 바로 서비스 메소드를 호출
        PostLikeDto dto = redisBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{userId}/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likeComment(@PathVariable Integer userId,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer commentId) {
        // 테스트용으로 바로 서비스 호출
        CommentLikeDto dto = redisBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
