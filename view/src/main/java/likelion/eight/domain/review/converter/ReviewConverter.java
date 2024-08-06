package likelion.eight.domain.review.converter;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;

public class ReviewConverter {

    // 모델(DTO)을 -> 엔티티로 변경
    public static ReviewEntity toReviewEntity(Review review, CourseEntity courseEntity, UserEntity userEntity) {

        return ReviewEntity.builder()
                .id(review.getId())
                .courseEntity(courseEntity)
                .userEntity(userEntity)
                .title(review.getTitle())
                .oneLineReview(review.getOneLineReview())
                .advantages(review.getAdvantages())
                .disadvantages(review.getDisadvantages())
                .instructorEvaluation(review.getInstructorEvaluation())
                .rating(review.getRating())
                .viewCount(review.getViewCount())
                .registrationDate(review.getRegistrationDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .build();
    }

    // 엔티티를 모델(DTO)로 변경
    public static Review toDto(ReviewEntity reviewEntity) {
        return Review.builder()
                .id(reviewEntity.getId())
                .courseId(reviewEntity.getCourseEntity().getId())
                .userId(reviewEntity.getUserEntity().getId())
                .title((reviewEntity.getTitle()))
                .oneLineReview(reviewEntity.getOneLineReview())
                .advantages(reviewEntity.getAdvantages())
                .disadvantages(reviewEntity.getDisadvantages())
                .instructorEvaluation(reviewEntity.getInstructorEvaluation())
                .rating(reviewEntity.getRating())
                .viewCount(reviewEntity.getViewCount())
                .registrationDate(reviewEntity.getRegistrationDate())
                .lastModifiedDate(reviewEntity.getLastModifiedDate())
                .build();
    }

}
