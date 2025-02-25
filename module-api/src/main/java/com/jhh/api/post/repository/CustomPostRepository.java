package com.jhh.api.post.repository;

import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.core.domain.post.QPost;
import com.jhh.core.domain.post_like_count.QPostLikeCount;
import com.jhh.core.domain.user_post_like.QUserPostLike;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomPostRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<PostResponseDto> getPostById(final Integer userId, final Integer postId) {
        QPost post = QPost.post;
        QUserPostLike userPostLike = QUserPostLike.userPostLike;
        QPostLikeCount postLikeCount = QPostLikeCount.postLikeCount;

         return Optional.ofNullable(queryFactory
                 .select(Projections.bean(
                         PostResponseDto.class,
                         post.id,
                         post.content,
                         postLikeCount.likeCount,
                         new CaseBuilder()
                                 .when(userPostLike.isNotNull())
                                 .then(true)
                                 .otherwise(false)
                                 .as("liked")
                 ))
                 .from(post)
                 .join(postLikeCount)
                 .on(postLikeCount.post.id.eq(post.id))
                 .leftJoin(userPostLike)
                 .on(post.id.eq(userPostLike.post.id))
                 .where(post.user.id.eq(userId))
                 .where(post.id.eq(postId))
                 .fetchOne());
    }
}
