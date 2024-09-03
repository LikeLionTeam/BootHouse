package likelion.eight.domain.course.model;

import likelion.eight.course.ParticipationTime;
import lombok.Builder;
import lombok.Data;


@Data
public class CrawledCourses {

    private final String bootcamp; // 훈련과정을 주관하는 교육기관 매핑

    private final String category; // 훈련과정 카테고리 -- UX/UI 디자인, 백엔드/자바

    private final String subCourse; // 각 코스가 어떤 subCategory에 속할지 추가

    private final String name; // 프로그램 이름

    private final String startDate; // 수업 시작일

    private final String endDate; // 수업 종료일

    private final String  closingDate; // 모집 마감일

    private final boolean codingTestExempt; // 코딩 테스트 여부

    private final boolean cardRequirement; // 내일배움카드 여부

    private final boolean onlineOffline; // 온라인,오프라인 여부 (T : 온라인, F : 오프라인)

    private final String location; // 오프라인 경우의 장소 (온라인일 경우에, 해당필드 무시)

    private final String tuitionType; // "유료" or "무료" (교육비용)

    private final String summary; // 과정 요약

    private final ParticipationTime participationTime; // 참여시간

    private final int maxParticipants; // 모집정원 :: -1 값이면, 모집정원 없음


    @Builder
    public CrawledCourses(String bootcamp, String category, String subCourse, String name, String startDate, String endDate, String closingDate, boolean codingTestExempt, boolean cardRequirement, boolean onlineOffline, String location, String tuitionType, String summary, ParticipationTime participationTime, int maxParticipants) {
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
    }
}
