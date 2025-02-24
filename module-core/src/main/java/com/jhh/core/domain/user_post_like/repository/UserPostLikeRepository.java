package com.jhh.core.domain.user_post_like.repository;

import com.jhh.core.domain.user_post_like.UserPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserPostLikeRepository extends JpaRepository<UserPostLike, Integer>, QuerydslPredicateExecutor<UserPostLike> {
    Optional<UserPostLike> findByUserIdAndPostId(Integer userId, Integer postId);

}
