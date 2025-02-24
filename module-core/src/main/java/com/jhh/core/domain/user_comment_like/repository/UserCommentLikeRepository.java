package com.jhh.core.domain.user_comment_like.repository;

import com.jhh.core.domain.user_comment_like.UserCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserCommentLikeRepository extends JpaRepository<UserCommentLike, Integer>, QuerydslPredicateExecutor<UserCommentLike> {
    Optional<UserCommentLike> findByUserIdAndCommentId(Integer userId, Integer commentId);
}
