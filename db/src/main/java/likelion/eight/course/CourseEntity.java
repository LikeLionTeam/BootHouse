package likelion.eight.course;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.category.CategoryEntity;
import likelion.eight.userCourse.UserCourseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private int maxParticipants; // 모집정원 :: -1 값이면, 모집정원 없음

    @Column(precision = 3, scale = 2) // 가능값 : 9.99, 0.01, 불가능값 : 10.00
    private BigDecimal averageRating; // 해당 프로그램 평균별점 (리뷰로부터 계산)

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseEntity> users = new ArrayList<>();

    public void addUser(UserCourseEntity userCourse) {
        if (userCourse == null) {
            throw new IllegalArgumentException("UserCourseEntity cannot be null");
        }
        users.add(userCourse);
        userCourse.setCourseEntity(this);
    }

    // 모집정원이 있는지 없는지 판단로직
    public boolean hasNoLimit(){
        return maxParticipants == -1;
    }

    // 모집정원을 가져올 때, -1인 경우를 처리하는 getter
    public int getMaxParticipants(){
        if (maxParticipants == -1){
            return Integer.MAX_VALUE; // 정원이 없는경우, 최대값 반환
        }
        return maxParticipants;
    }


}

