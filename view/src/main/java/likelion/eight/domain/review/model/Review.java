package likelion.eight.domain.review.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Review {

    private Long id;
    private Long courseId;
    private String title;
    private String oneLineReview;
    private String advantages;
    private String disadvantages;
    private String instructorEvaluation;
    private Integer rating;

}
