package com.jhh.api.post.service.redis_base;

import com.jhh.api.post.dto.PostResponseDto;

public interface RedisBasePostService {
    PostResponseDto getPost(Integer userId, Integer postId);
}
