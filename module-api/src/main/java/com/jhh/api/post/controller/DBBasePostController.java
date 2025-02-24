package com.jhh.api.post.controller;

import com.jhh.api.common.exception.NotFoundException;
import com.jhh.api.common.response.BaseResponse;
import com.jhh.api.common.response.BaseResponseErrorStatus;
import com.jhh.api.post.dto.CommentLikeDto;
import com.jhh.api.post.dto.PostLikeDto;
import com.jhh.api.post.service.DBBaseLikeService;
import com.jhh.core.domain.comment.service.CommentService;
import com.jhh.core.domain.post.service.PostService;
import com.jhh.core.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db-base/post")
@RequiredArgsConstructor
public class DBBasePostController {

    private final DBBaseLikeService dbBaseLikeService;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/{postId}/like")
    public BaseResponse<PostLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable Integer postId) {

        Integer userId = Integer.valueOf(jwt.getId());

        if (!userService.isUserExists(userId)) {
            throw new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_USER);
        }

        if (!postService.isPostExists(postId)) {
            throw new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_POST);
        }

        PostLikeDto dto = dbBaseLikeService.likePost(userId, postId);
        return BaseResponse.ok(dto);
    }

    @PostMapping("/{postId}/{commentId}/like")
    public BaseResponse<CommentLikeDto> likePost(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable Integer postId,
                                                 @PathVariable Integer commentId) {
        Integer userId = Integer.valueOf(jwt.getId());

        if (!userService.isUserExists(userId)) {
            throw new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_USER);
        }

        if (!postService.isPostExists(postId)) {
            throw new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_POST);
        }

        if (!commentService.isCommentExists(commentId)) {
            throw new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_COMMENT);
        }

        CommentLikeDto dto = dbBaseLikeService.likeComment(userId, postId, commentId);
        return BaseResponse.ok(dto);
    }
}
