package likelion.eight.likeReview;

import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeReviewJpaRepository extends JpaRepository<LikeReviewEntity,Long> {

    List<LikeReviewEntity> findAllByUserEntity(UserEntity userEntity);

    Optional<LikeReviewEntity> findByUserEntityAndReviewEntity(UserEntity userEntity, ReviewEntity reviewEntity);
}
