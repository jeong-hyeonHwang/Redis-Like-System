package com.jhh.api.post.service.redis_base;

import com.jhh.api.common.exception.NotFoundException;
import com.jhh.api.common.response.BaseResponseErrorStatus;
import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.api.post.repository.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisBasePostServiceImpl implements RedisBasePostService {
    private final CustomPostRepository customPostRepository;
    private final RedisBaseLikeService redisBaseLikeService;

    @Override
    public PostResponseDto getPost(Integer userId, Integer postId) {
        PostResponseDto dto = customPostRepository.getPostById3(userId, postId)
                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_POST));

        dto.setLiked(redisBaseLikeService.isUserLikedPost(userId, postId));
        dto.setLikeCount(redisBaseLikeService.getPostLikeCount(postId));

        return dto;
    }
}
