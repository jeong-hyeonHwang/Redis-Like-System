package com.jhh.core.domain.post.service;

import com.jhh.core.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public boolean isPostExists(Integer postId) {
        return postRepository.existsById(postId);
    }
}
