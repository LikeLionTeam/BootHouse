package likelion.eight.domain.course.controller.model;

import likelion.eight.course.ParticipationTime;
import likelion.eight.domain.course.model.Course;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourseDetailDto {
    private Long id;
    private String name;
    private String recruitmentStatusDescription;
    private String summary;
    private String logo;
    private String bootcampName;
    private String bootcampDescription;
    private String bootcampLocation;
    private String bootcampUrl;
    private LocalDateTime closingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean onlineOffline;
    private String location;
    private int maxParticipants;
    private String tuitionType;
    private boolean cardRequirement;
    private BigDecimal averageRating;
    private String defaultSchedule;
    private boolean codingTestExempt;

    public static CourseDetailDto from(Course course){
        CourseDetailDto dto = new CourseDetailDto();

        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setRecruitmentStatusDescription(course.getRecruitmentStatus().getDescription());
        dto.setSummary(course.getSummary());
        // Course - Bootcamp 불러옴
        dto.setLogo(course.getBootcamp().getLogo());
        dto.setBootcampName(course.getBootcamp().getName());
        dto.setBootcampDescription(course.getBootcamp().getDescription());
        dto.setBootcampDescription(course.getBootcamp().getLocation());
        dto.setBootcampUrl(course.getBootcamp().getUrl());

        dto.setClosingDate(course.getClosingDate());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        dto.setOnlineOffline(course.isOnlineOffline());
        dto.setLocation(course.getLocation());
        dto.setMaxParticipants(course.getMaxParticipants());
        dto.setTuitionType(course.getTuitionType());
        dto.setCardRequirement(course.isCardRequirement());
        dto.setAverageRating(course.getAverageRating());

        String defaultSchedule = course.getParticipationTime().getDefaultSchedule().length() > 0 ? course.getParticipationTime().getDefaultSchedule(): course.getParticipationTime().getDisplayName() ;
        dto.setDefaultSchedule(defaultSchedule);

        dto.setCodingTestExempt(course.isCodingTestExempt());
        return dto;
    }
}
