package likelion.eight.likeReview;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "like_review")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReviewEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;

    @Builder
    public LikeReviewEntity(UserEntity userEntity, ReviewEntity reviewEntity) {
        this.userEntity = userEntity;
        this.reviewEntity = reviewEntity;
    }
}
