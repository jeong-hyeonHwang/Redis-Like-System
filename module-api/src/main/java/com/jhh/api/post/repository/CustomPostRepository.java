package com.jhh.api.post.repository;

import com.jhh.api.post.dto.PostResponseDto;
import com.jhh.core.domain.post.QPost;
import com.jhh.core.domain.post_like_count.QPostLikeCount;
import com.jhh.core.domain.user_post_like.QUserPostLike;
import com.jhh.core.domain.user_post_like.UserPostLike;
import com.jhh.core.domain.user_post_like.repository.UserPostLikeRepository;
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
    private final UserPostLikeRepository userPostLikeRepository;

    // 단일 쿼리
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
                                 .as("isLiked")
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

    // 쿼리 호출 3번
    public Optional<PostResponseDto> getPostById2(final Integer userId, final Integer postId) {
        QPost post = QPost.post;
        QUserPostLike userPostLike = QUserPostLike.userPostLike;
        QPostLikeCount postLikeCount = QPostLikeCount.postLikeCount;

        PostResponseDto dto = queryFactory
                .select(Projections.bean(
                        PostResponseDto.class,
                        post.id,
                        post.content
                ))
                .from(post)
                .where(
                        post.user.id.eq(userId),
                        post.id.eq(postId))
                .fetchOne();

        Integer countValue = queryFactory
                .select(postLikeCount.likeCount)
                .from(postLikeCount)
                .where(postLikeCount.post.id.eq(post.id))
                .fetchOne();


        assert dto != null;
        dto.setLikeCount(countValue);

        UserPostLike likeValue = queryFactory
                .select(userPostLike)
                .from(userPostLike)
                .where(
                        userPostLike.post.id.eq(post.id),
                        userPostLike.user.id.eq(userId))
                .fetchOne();

        dto.setLiked(likeValue != null);
        return Optional.of(dto);
    }

    // 쿼리 호출 3번 좋아요 관련 데이터 제외 (Post 데이터 Only)
    public Optional<PostResponseDto> getPostById3(final Integer userId, final Integer postId) {
        QPost post = QPost.post;

        PostResponseDto dto = queryFactory
                .select(Projections.bean(
                        PostResponseDto.class,
                        post.id,
                        post.content
                ))
                .from(post)
                .where(post.user.id.eq(userId))
                .where(post.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(dto);
    }
}
