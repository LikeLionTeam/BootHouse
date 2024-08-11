package likelion.eight.likeReview;

import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeReviewJpaRepository extends JpaRepository<LikeReviewEntity,Long> {

    Page<LikeReviewEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);

    Optional<LikeReviewEntity> findByUserEntityAndReviewEntity(UserEntity userEntity, ReviewEntity reviewEntity);
}
