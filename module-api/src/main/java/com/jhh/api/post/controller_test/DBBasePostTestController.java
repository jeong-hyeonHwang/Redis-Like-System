package com.jhh.api.post.controller_test;

import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.service.DBBaseLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db-base/test/post")
@RequiredArgsConstructor
public class DBBasePostTestController {

    private final DBBaseLikeService dbBaseLikeService;

    @PostMapping("/{userId}/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@PathVariable Integer userId,
                                              @PathVariable Integer postId) {
        // 테스트용으로 바로 서비스 호출
        PostLikeDto dto = dbBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{userId}/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likeComment(@PathVariable Integer userId,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer commentId) {
        // 테스트용으로 바로 서비스 호출
        CommentLikeDto dto = dbBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
