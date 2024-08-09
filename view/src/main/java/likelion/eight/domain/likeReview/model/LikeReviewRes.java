package likelion.eight.domain.likeReview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class LikeReviewRes {
    private String title;
    private String oneLineReview;
    private Integer rating;
    private String author;
}
