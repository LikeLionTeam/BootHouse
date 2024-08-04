package likelion.eight.domain.review.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private LocalDateTime registrationDate;
    private LocalDateTime lastModifiedDate;


}
