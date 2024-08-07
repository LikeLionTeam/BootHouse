package likelion.eight.review.ifs;

import likelion.eight.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByUserEntityIdAndCourseEntityId(Long userId, Long courseId);


    Optional<ReviewEntity> findByCourseEntityIdAndUserEntityId(Long courseId, Long userId);

}
