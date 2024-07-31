package likelion.eight.course;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.BaseTimeEntity;
import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.category.CategoryEntity;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id")
    private BootCampEntity bootCampEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(nullable = false)
    private int duration; // 기간일수

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String instructor;

    @Column(nullable = false, name = "training_allowance")
    private int trainingAllowance;

    @Column(nullable = false, name = "card_requirement")
    private boolean cardRequirement;
}