package likelion.eight.review.ifs;

import likelion.eight.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByUserEntityIdAndCourseEntityId(Long userId, Long courseId);


    Optional<ReviewEntity> findByCourseEntityIdAndUserEntityId(Long courseId, Long userId);

    @Query("select r from ReviewEntity r where r.courseEntity.id = :courseId")
    List<ReviewEntity> findByCourseId(Long courseId);
}
