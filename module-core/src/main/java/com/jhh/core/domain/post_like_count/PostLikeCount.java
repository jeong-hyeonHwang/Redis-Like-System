package com.jhh.core.domain.post_like_count;

import com.jhh.core.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_like_counts", schema = "likedemo")
public class PostLikeCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_count_id", columnDefinition = "INT")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "like_count", columnDefinition = "INT")
    private Integer likeCount;
}
