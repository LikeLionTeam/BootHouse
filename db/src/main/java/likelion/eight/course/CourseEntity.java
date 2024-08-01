package likelion.eight.course;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.category.CategoryEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class CourseEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id")
    private BootCampEntity bootcampEntity; // 훈련과정을 주관하는 교육기관 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity; // 훈련과정 카테고리 -- UX/UI 디자인, 백엔드/자바

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_course_id")
    private SubCourseEntity subCourseEntity; // 각 코스가 어떤 subCategory에 속할지 추가

    @Column(length = 255, nullable = false)
    private String name; // 프로그램 이름

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate; // 수업 시작일

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate; // 수업 종료일

    @Column(nullable = false, name = "closing_date")
    private LocalDateTime closingDate; // 모집 마감일

    @Column(name = "coding_test_exempt", nullable = false)
    private boolean codingTestExempt; // 코딩 테스트 여부

    @Column(nullable = false, name = "card_requirement")
    private boolean cardRequirement; // 내일배움카드 여부

    @Column(nullable = false, name = "online_offline")
    private boolean onlineOffline; // 온라인,오프라인 여부 (T : 온라인, F : 오프라인)

    @Column(length = 255, name = "location")
    private String location; // 오프라인 경우의 장소 (온라인일 경우에, 해당필드 무시)

    @Column(length = 255, nullable = false, name = "tuition_type")
    private String tuitionType; // "유료" or "무료" (교육비용)

    @Column(columnDefinition = "TEXT", name = "summary")
    private String summary; // 과정 요약

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_time", nullable = false)
    private ParticipationTime participationTime; // 참여시간

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants; // 모집정원

    @Column(precision = 3, scale = 2) // 가능값 : 9.99, 0.01, 불가능값: 10.00
    private BigDecimal averageRating; // 해당 프로그램 평균 평점

    @Column(nullable = false, name = "view_counts")
    private int viewCounts; // 해당 프로그램 조회수 (정렬을 위해)
}
