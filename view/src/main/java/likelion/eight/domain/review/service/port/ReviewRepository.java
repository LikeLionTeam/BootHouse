package likelion.eight.domain.review.service.port;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review, CourseEntity courseEntity, UserEntity userEntity);

    Review getById(Long id);

    Optional<Review> findById(Long id);

    Optional<Review> findReviewByCourseId(Long courseId);

    List<Review> findByCourseId(Long courseId);

    List<Review> findAll();

    void deleteById(Long id);


    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Review> findByCourseIdAndUserId(Long courseId, Long userId);
}

