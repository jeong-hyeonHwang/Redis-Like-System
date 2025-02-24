package com.jhh.core.domain.post.repository;

import com.jhh.core.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends JpaRepository<Post, Integer>, QuerydslPredicateExecutor<Post> {
}
