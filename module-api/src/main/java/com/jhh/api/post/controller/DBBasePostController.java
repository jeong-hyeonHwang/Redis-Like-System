package com.jhh.api.post.controller;

import co.elastic.clients.elasticsearch.license.PostResponse;
import com.jhh.api.common.exception.NotFoundException;
import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.common.response.BaseResponseErrorStatus;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.api.post.service.db_base.DBBaseLikeService;
import com.jhh.api.post.service.db_base.DBBasePostService;
import com.jhh.core.domain.comment.service.CommentService;
import com.jhh.core.domain.post.service.PostService;
import com.jhh.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db-base/posts")
@RequiredArgsConstructor
public class DBBasePostController {

    private final DBBasePostService dbBasePostService;
    private final DBBaseLikeService dbBaseLikeService;

    @GetMapping("/{postId}")
    public BaseResponse<PostResponseDto> getPost(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Integer postId) {
        Integer userId = Integer.valueOf(jwt.getId());
        PostResponseDto dto = dbBasePostService.getPost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Integer postId) {

        Integer userId = Integer.valueOf(jwt.getId());
        PostLikeDto dto = dbBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Integer postId,
                                                 @PathVariable Integer commentId) {
        Integer userId = Integer.valueOf(jwt.getId());
        CommentLikeDto dto = dbBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
