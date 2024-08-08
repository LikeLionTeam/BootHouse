package likelion.eight.domain.review.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.service.port.CourseRepository;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.converter.ReviewConverter;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;


    @Transactional(readOnly = true)
    public List<Review> findAllReviews() {

        return reviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found : "));
    }

    @Transactional
    public void saveReview(ReviewCreateRequest reviewCreateRequest, Long userId, Long courseId) {

        CourseEntity courseEntity = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + userId));

        UserEntity userEntity = UserConverter.toEntity(user);

        Review review = Review.builder()
                .title(reviewCreateRequest.getTitle())
                .oneLineReview(reviewCreateRequest.getOneLineReview())
                .advantages(reviewCreateRequest.getAdvantages())
                .disadvantages(reviewCreateRequest.getDisadvantages())
                .instructorEvaluation(reviewCreateRequest.getInstructorEvaluation())
                .rating(reviewCreateRequest.getRating())
                .courseId(courseId)
                .userId(userId)
                .viewCount(0) //기본 값
                .build();

        reviewRepository.save(review, courseEntity, userEntity);

    }

    public void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found "));

        CourseEntity courseEntity = courseRepository.findByCourseId(review.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        User user = userRepository.findById(review.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + review.getUserId()));

        UserEntity userEntity = UserConverter.toEntity(user);

        review.update(reviewUpdateRequest, clockHolder);

        reviewRepository.save(review, courseEntity,userEntity);

    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public boolean existsByUserIdAndCourseId(Long userId, Long courseId) {
        return reviewRepository.existsByUserIdAndCourseId(userId, courseId);
    }


    public Review findReviewByCourseIdAndUserId(Long courseId, Long userId) {
        return reviewRepository.findByCourseIdAndUserId(courseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found"));
    }

    public void incrementViewcount(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found"));

        CourseEntity courseEntity = courseRepository.findByCourseId(review.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        User user = userRepository.findById(review.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        UserEntity userEntity = UserConverter.toEntity(user);

        ReviewEntity reviewEntity = ReviewConverter.toReviewEntity(review, courseEntity, userEntity);

        reviewEntity.incrementViewCount();

        Review reviewDto = ReviewConverter.toDto(reviewEntity);

        reviewRepository.save(reviewDto, courseEntity, userEntity);

    }

    public List<Review> searchReviews(ReviewSearchCondition condition) {
        if (condition.getKeyword() == null || condition.getKeyword().isEmpty()) {
            return findAllReviews();
        }
        List<ReviewEntity> reviewEntities = reviewRepository.searchByKeyword(condition.getKeyword());
        return reviewEntities.stream().map(ReviewConverter::toDto).collect(Collectors.toList());
    }

}
