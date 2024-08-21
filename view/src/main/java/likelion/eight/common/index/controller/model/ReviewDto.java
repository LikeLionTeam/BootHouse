package likelion.eight.common.index.controller.model;

import likelion.eight.domain.review.model.Review;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String username;
    private String oneLineReview;

    public ReviewDto(Review review, String username){
        this.id = review.getId();
        this.username = username;
        this.oneLineReview = review.getOneLineReview();
    }
}
