package likelion.eight.domain.review.converter;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.review.ReviewEntity;

public class ReviewConverter {

    public static ReviewEntity toReviewEntity(Review review, CourseEntity courseEntity) {

        return ReviewEntity.builder()
                .id(review.getId())
                .courseEntity(courseEntity)
                .title(review.getTitle())
                .oneLineReview(review.getOneLineReview())
                .advantages(review.getAdvantages())
                .disadvantages(review.getDisadvantages())
                .instructorEvaluation(review.getInstructorEvaluation())
                .rating(review.getRating())
                .build();
    }

    public static Review toDto(ReviewEntity reviewEntity) {
        return Review.builder()
                .id(reviewEntity.getId())
                .courseId(reviewEntity.getCourseEntity().getId())
                .title((reviewEntity.getTitle()))
                .oneLineReview(reviewEntity.getOneLineReview())
                .advantages(reviewEntity.getAdvantages())
                .disadvantages(reviewEntity.getDisadvantages())
                .instructorEvaluation(reviewEntity.getInstructorEvaluation())
                .rating(reviewEntity.getRating())
                .build();
    }

    public static Review toReview(ReviewCreateRequest request) {
        return Review.builder()
                .title(request.getTitle())
                .oneLineReview(request.getOneLineReview())
                .advantages(request.getAdvantages())
                .disadvantages(request.getDisadvantages())
                .instructorEvaluation(request.getInstructorEvaluation())
                .rating(request.getRating())
                .build();
    }

}
