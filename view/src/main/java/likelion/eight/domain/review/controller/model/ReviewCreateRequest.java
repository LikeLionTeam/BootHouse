package likelion.eight.domain.review.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCreateRequest {
    private String title;
    private String oneLineReview;
    private String advantages;
    private String disadvantages;
    private String instructorEvaluation;
    private Integer rating;
}
