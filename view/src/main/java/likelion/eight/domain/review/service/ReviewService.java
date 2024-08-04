package likelion.eight.domain.review.service;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.service.port.CourseRepository;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.converter.ReviewConverter;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.review.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        CourseEntity courseEntity = courseRepository.findById(review.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + review.getCourseId()));

        reviewRepository.save(review, courseEntity);

    }

    public void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found "));

        CourseEntity courseEntity = courseRepository.findById(review.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Coruse not found"));

        review.update(reviewUpdateRequest);

        reviewRepository.save(review, courseEntity);

    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
