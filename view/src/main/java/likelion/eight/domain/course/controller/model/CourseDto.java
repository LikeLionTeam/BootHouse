package likelion.eight.domain.course.controller.model;

import com.querydsl.core.annotations.QueryProjection;
import likelion.eight.course.ParticipationTime;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.model.RecruitmentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String subCourseName;
    private final RecruitmentStatus recruitmentStatus; // querydsl 설정 중 추가
    private String recruitmentStatusDescription;
    private LocalDateTime closingDate;
    private String tuitionType;
    private String onlineOffline;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String participationTimeDisplayName;
    private String participationTimeDefaultSchedule;
    private String selectionProcedure;

    public CourseDto(Course course){
        this.id = course.getId();
        this.name = course.getName();
        this.subCourseName = course.getSubCourse() != null ? course.getSubCourse().getName() : "프로그램 과정명 없음";
        this.recruitmentStatusDescription = course.getRecruitmentStatus().getDescription();
        this.closingDate = course.getClosingDate();
        this.tuitionType = course.getTuitionType();
        this.onlineOffline = course.isOnlineOffline() ? "온라인" : "오프라인";
        this.location = course.isOnlineOffline() ? "" : course.getLocation();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
        this.participationTimeDisplayName = course.getParticipationTime().getDisplayName();
        this.participationTimeDefaultSchedule = course.getParticipationTime().getDefaultSchedule();
        this.selectionProcedure = course.isCodingTestExempt() ? "코딩 테스트 있음" : "코딩 테스트 없음";

        this.recruitmentStatus = null;
    }

    @QueryProjection
    public CourseDto(Long id, String name, String subCourseName, LocalDateTime closingDate,
                     String tuitionType, boolean onlineOffline, String location, LocalDate startDate,
                     LocalDate endDate, ParticipationTime participationTime, boolean codingTestExempt){
        this.id = id;
        this.name = name;
        this.subCourseName = subCourseName != null ? subCourseName : "프로그램 과정명 없음";

        // Entity에는 없고, Course 모델에서만 추가한 값
        this.recruitmentStatus = Course.determineRecruitmentStatus(closingDate);
        this.recruitmentStatusDescription = recruitmentStatus.getDescription();

        this.closingDate = closingDate;
        this.tuitionType = tuitionType;
        this.onlineOffline = onlineOffline ? "온라인" : "오프라인";
        this.location = onlineOffline ? "" : location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participationTimeDisplayName = participationTime.getDisplayName();
        this.participationTimeDefaultSchedule = participationTime.getDefaultSchedule();
        this.selectionProcedure = codingTestExempt ? "코딩 테스트 있음" : "코딩 테스트 없음";

    }

}
