package likelion.eight.review.ifs;

import likelion.eight.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByUserEntityIdAndCourseEntityId(Long userId, Long courseId);


    Optional<ReviewEntity> findByCourseEntityIdAndUserEntityId(Long courseId, Long userId);

    @Query("select r from ReviewEntity r where r.courseEntity.id = :courseId")
    List<ReviewEntity> findByCourseId(Long courseId);

    List<ReviewEntity> findByTitleContainingAndOneLineReviewContaining(String title, String oneLineReview);

    @Query("SELECT r FROM ReviewEntity r WHERE " +
            "LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.oneLineReview) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.advantages) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.disadvantages) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.instructorEvaluation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ReviewEntity> searchByKeyword(@Param("keyword") String keyword);

    Optional<ReviewEntity> findTopByIdLessThanOrderByIdDesc(Long id);
    Optional<ReviewEntity> findTopByIdGreaterThanOrderByIdAsc(Long id);
}
