package com.jhh.api.post.controller_test;

import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.service.db_base.DBBaseLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db-base/test/posts")
@RequiredArgsConstructor
public class DBBasePostTestController {

    private final DBBaseLikeService dbBaseLikeService;

    @PostMapping("/{userId}/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@PathVariable Integer userId,
                                              @PathVariable Integer postId) {
        PostLikeDto dto = dbBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{userId}/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likeComment(@PathVariable Integer userId,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer commentId) {
        CommentLikeDto dto = dbBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
