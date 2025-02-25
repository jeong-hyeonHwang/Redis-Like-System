package com.jhh.api.post.controller_test;

import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.api.post.service.db_base.DBBaseLikeService;
import com.jhh.api.post.service.db_base.DBBasePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db-base/test/posts")
@RequiredArgsConstructor
public class DBBasePostTestController {

    private final DBBaseLikeService dbBaseLikeService;
    private final DBBasePostService dbBasePostService;

    @PostMapping("/{userId}/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@PathVariable Integer userId,
                                              @PathVariable Integer postId) {
        PostLikeDto dto = dbBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @GetMapping("/{userId}/{postId}")
    public BaseResponse<PostResponseDto> getPost(@PathVariable Integer userId,
                                                @PathVariable Integer postId) {
        PostResponseDto dto = dbBasePostService.getPost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @GetMapping("/{userId}/{postId}/2")
    public BaseResponse<PostResponseDto> getPost2(@PathVariable Integer userId,
                                                 @PathVariable Integer postId) {
        PostResponseDto dto = dbBasePostService.getPost2(userId, postId);
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
