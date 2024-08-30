package likelion.eight.domain.course.model;


import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.course.ParticipationTime;
import likelion.eight.domain.category.model.Category;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.subcourse.model.SubCourse;
import likelion.eight.domain.bootcamp.model.Bootcamp;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Course {

    private final Long id;

    private final Bootcamp bootcamp; // 훈련과정을 주관하는 교육기관 매핑

    private final Category category; // 훈련과정 카테고리 -- UX/UI 디자인, 백엔드/자바

    private final SubCourse subCourse; // 각 코스가 어떤 subCategory에 속할지 추가

    private final String name; // 프로그램 이름

    private final LocalDate startDate; // 수업 시작일

    private final LocalDate endDate; // 수업 종료일

    private final LocalDateTime closingDate; // 모집 마감일

    private final boolean codingTestExempt; // 코딩 테스트 여부

    private final boolean cardRequirement; // 내일배움카드 여부

    private final boolean onlineOffline; // 온라인,오프라인 여부 (T : 온라인, F : 오프라인)

    private final String location; // 오프라인 경우의 장소 (온라인일 경우에, 해당필드 무시)

    private final String tuitionType; // "유료" or "무료" (교육비용)

    private final String summary; // 과정 요약

    private final ParticipationTime participationTime; // 참여시간

    private final int maxParticipants; // 모집정원 :: -1 값이면, 모집정원 없음

    private BigDecimal averageRating; // 해당 course에 대한 평균별점

    // 엔디티에는 없지만, dto에는 추가
    private final RecruitmentStatus recruitmentStatus; // 모집 상태

    @Builder
    public Course(Long id, Bootcamp bootcamp, Category category, SubCourse subCourse, String name, LocalDate startDate, LocalDate endDate, LocalDateTime closingDate,
                  boolean codingTestExempt, boolean cardRequirement, boolean onlineOffline, String location, String tuitionType, String summary, ParticipationTime participationTime, int maxParticipants
                    , BigDecimal averageRating) {
        this.id = id;
        this.bootcamp = bootcamp;
        this.category = category;
        this.subCourse = subCourse;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closingDate = closingDate;
        this.codingTestExempt = codingTestExempt;
        this.cardRequirement = cardRequirement;
        this.onlineOffline = onlineOffline;
        this.location = location;
        this.tuitionType = tuitionType;
        this.summary = summary;
        this.participationTime = participationTime;
        this.maxParticipants = maxParticipants;
        this.averageRating = averageRating;

        // 모집상태 초기화
        this.recruitmentStatus = determineRecruitmentStatus(this.closingDate);
    }

    public static RecruitmentStatus determineRecruitmentStatus(LocalDateTime closingDate) {
        LocalDate now = LocalDate.now();

        if (now.isBefore(closingDate.toLocalDate())){
            return RecruitmentStatus.OPEN;
        } else {
            return RecruitmentStatus.CLOSED;
        }
    }

    // 특정 course에 대한 평균 평점 계산로직
    public void calculateAverageRating(List<Review> reviews){
        if (reviews == null || reviews.isEmpty()){
            this.averageRating = BigDecimal.ZERO;
        } else {
            double sum = reviews
                    .stream()
                    .mapToInt(Review::getRating)
                    .sum();

            this.averageRating = BigDecimal.valueOf(sum / reviews.size());
        }
    }
}
