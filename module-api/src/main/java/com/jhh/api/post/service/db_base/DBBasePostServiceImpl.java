package com.jhh.api.post.service.db_base;

import com.jhh.api.common.exception.NotFoundException;
import com.jhh.api.common.response.BaseResponseErrorStatus;
import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.api.post.repository.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DBBasePostServiceImpl implements DBBasePostService {
    private final CustomPostRepository customPostRepository;

    @Override
    public PostResponseDto getPost(Integer userId, Integer postId) {
        return customPostRepository.getPostById(userId, postId)
                .orElseThrow(() -> new NotFoundException(BaseResponseErrorStatus.NOT_EXIST_POST));
    }
}
