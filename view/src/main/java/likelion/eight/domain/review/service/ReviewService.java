package likelion.eight.domain.review.service;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.service.port.CourseRepository;
import likelion.eight.domain.review.converter.ReviewConverter;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.review.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<Review> findAllReviews() {

        return reviewRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found : "));
    }

    @Transactional
    public void saveReview(Review review) {

        /*CourseEntity courseEntity = courseRepository.findById(review.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course Not Found"));*/

        CourseEntity courseEntity = courseRepository.findById(review.getCourseId())  // 임시로 course_id를 1로 설정
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + review.getCourseId()));

        ReviewEntity reviewEntity = ReviewConverter.toReviewEntity(review, courseEntity);
        Review review2 = ReviewConverter.toDto(reviewEntity);

        reviewRepository.save(review2, courseEntity);

    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
