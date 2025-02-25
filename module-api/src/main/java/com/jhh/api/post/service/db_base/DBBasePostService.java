package com.jhh.api.post.service.db_base;

import com.jhh.api.post.dto.PostResponseDto;

public interface DBBasePostService {
    PostResponseDto getPost(Integer userId, Integer postId);

    PostResponseDto getPost2(Integer userId, Integer postId);
}
