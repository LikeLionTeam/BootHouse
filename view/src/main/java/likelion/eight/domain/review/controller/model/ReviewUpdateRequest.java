package likelion.eight.domain.review.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
// 리뷰 수정 시 사용하는 DTO
public class ReviewUpdateRequest {
    private String title;
    private String oneLineReview;
    private String advantages;
    private String disadvantages;
    private String instructorEvaluation;
    private Integer rating;
}
