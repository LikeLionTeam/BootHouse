package likelion.eight.domain.review.converter;

import jakarta.persistence.metamodel.IdentifiableType;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.user.model.User;
import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;

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

    public static Review toReview(ReviewEntity reviewEntity) {
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

}
