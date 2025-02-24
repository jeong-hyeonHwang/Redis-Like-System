package com.jhh.core.domain.comment.service;

import com.jhh.core.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public boolean isCommentExists(Integer commentId) {
        return commentRepository.existsById(commentId);
    }
}