package com.jhh.core.domain.comment_like_count.repository;

import com.jhh.core.domain.comment.Comment;
import com.jhh.core.domain.comment_like_count.CommentLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CommentLikeCountRepository extends JpaRepository<CommentLikeCount, Integer>, QuerydslPredicateExecutor<CommentLikeCount> {
    Optional<CommentLikeCount> findByComment(Comment comment);
}
