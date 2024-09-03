package likelion.eight.domain.review.service.port;

import aj.org.objectweb.asm.commons.Remapper;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.controller.model.ReviewSortCondition;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.user.model.User;
import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review, CourseEntity courseEntity, UserEntity userEntity);

    Review save(ReviewEntity reviewEntity, CourseEntity courseEntity, UserEntity userEntity);

    Optional<ReviewEntity> findById(Long id);

    List<Review> findByCourseId(Long courseId);

    Page<ReviewEntity> findAll(Pageable pageable);


    void deleteById(Long id);


    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Review> findByCourseIdAndUserId(Long courseId, Long userId);

    Page<ReviewEntity> searchByKeyword(ReviewSearchCondition reviewSearchCondition, Pageable pageable);


    Page<ReviewEntity> sortByCondition(ReviewSortCondition reviewSortCondition, Pageable pageable);


    Optional<ReviewEntity> findPreviousReview(Long reviewId);

    Optional<ReviewEntity> findNextReview(Long reviewId);

    List<ReviewEntity> findByUserEntityByUser(User user);

}

