package likelion.eight.domain.course.controller.model;

import likelion.eight.domain.review.model.Review;
import lombok.Data;
@Data
public class ReviewDto {
    private Long id;
    private String title;
    private String oneLineReview;

    public ReviewDto(Review review){
        this.id = review.getId();
        this.title = review.getTitle();
        this.oneLineReview = review.getOneLineReview();
    }
}
