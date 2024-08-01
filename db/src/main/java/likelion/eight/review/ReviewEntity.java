package likelion.eight.review;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.course.CourseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ReviewEntity extends BaseTimeEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity courseEntity;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false,length = 50)
    private String oneLineReview;

    @Column(nullable = false)
    private String advantages;

    @Column(nullable = false)
    private String disadvantages;

    @Column(nullable = false)
    private String instructorEvaluation;

    @Column(nullable = false)
    private Integer rating;

    private Integer viewCount=0;


    // view count ++
    public void incrementViewCount() {
        this.viewCount++;
    }

}