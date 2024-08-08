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
        return getReview(id);
    }

    @Transactional(readOnly = true)
    public Optional<Review> findPreviousReview(Long reviewId) {
        return reviewRepository.findPreviousReview(reviewId)
                .map(ReviewConverter::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Review> findNextReview(Long reviewId) {
        return reviewRepository.findNextReview(reviewId)
                .map(ReviewConverter::toDto);
    }


    @Transactional
    public void saveReview(ReviewCreateRequest reviewCreateRequest, Long userId, Long courseId) {

        CourseEntity courseEntity = getCourseEntity(courseId);
        UserEntity userEntity = getUserEntity(userId);

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
    @Transactional
    public void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = getReview(id);

        CourseEntity courseEntity = getCourseEntity(review.getCourseId());
        UserEntity userEntity = getUserEntity(review.getUserId());

        review.update(reviewUpdateRequest, clockHolder);

        reviewRepository.save(review, courseEntity,userEntity);

    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserIdAndCourseId(Long userId, Long courseId) {
        return reviewRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Transactional(readOnly = true)
    public Review findReviewByCourseIdAndUserId(Long courseId, Long userId) {
        return reviewRepository.findByCourseIdAndUserId(courseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found"));
    }

    @Transactional
    public void incrementViewcount(Long reviewId) {

        Review review = getReview(reviewId);
        CourseEntity courseEntity = getCourseEntity(review.getCourseId());
        UserEntity userEntity = getUserEntity(review.getUserId());

        ReviewEntity reviewEntity = ReviewConverter.toReviewEntity(review, courseEntity, userEntity);

        reviewEntity.incrementViewCount();

        reviewRepository.save(ReviewConverter.toDto(reviewEntity), courseEntity, userEntity);


    }

    @Transactional(readOnly = true)
    public List<Review> searchReviews(ReviewSearchCondition condition) {
        if (condition.getKeyword() == null || condition.getKeyword().isEmpty()) {
            return findAllReviews();
        }
        List<ReviewEntity> reviewEntities = reviewRepository.searchByKeyword(condition.getKeyword());
        return reviewEntities.stream().map(ReviewConverter::toDto).collect(Collectors.toList());
    }


    // 중복 함수 빼 놓음
    private Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found : "));
    }

    private CourseEntity getCourseEntity(Long courseId) {
        return courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
    }

    private UserEntity getUserEntity(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        return UserConverter.toEntity(user);
    }

}
