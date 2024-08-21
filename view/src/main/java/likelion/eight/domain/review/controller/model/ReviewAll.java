package likelion.eight.domain.review.controller.model;

import likelion.eight.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReviewAll {

    private Long reviewId;
    private String courseName;
    private String title;
    private String author;
    private String oneLineReview;
    private Integer rating;
    private Integer viewCount;
    private LocalDateTime registrationDate;

    // DTO 생성자
    public ReviewAll(Review review, String courseName, String author) {
        this.reviewId = review.getId();
        this.courseName = courseName;
        this.title = review.getTitle();
        this.author = author;
        this.oneLineReview = review.getOneLineReview();
        this.rating = review.getRating();
        this.viewCount = review.getViewCount();
        this.registrationDate = review.getRegistrationDate();
    }
}
