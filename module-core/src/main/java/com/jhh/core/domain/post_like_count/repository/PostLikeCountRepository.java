package com.jhh.core.domain.post_like_count.repository;

import com.jhh.core.domain.post.Post;
import com.jhh.core.domain.post_like_count.PostLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface PostLikeCountRepository extends JpaRepository<PostLikeCount, Integer>, QuerydslPredicateExecutor<PostLikeCount> {
    Optional<PostLikeCount> findByPost(Post post);
}
