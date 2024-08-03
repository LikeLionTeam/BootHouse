package likelion.eight.domain.course.converter;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.model.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseConverter {
    public static CourseEntity toEntity(Course course){
        return CourseEntity.builder()
                .id(course.getId())
                .bootcampEntity(course.getBootcampEntity())
                .categoryEntity(course.getCategoryEntity())
                .subCourseEntity(course.getSubCourseEntity())
                .name(course.getName())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .closingDate(course.getClosingDate())
                .codingTestExempt(course.isCodingTestExempt())
                .cardRequirement(course.isCardRequirement())
                .onlineOffline(course.isOnlineOffline())
                .location(course.getLocation())
                .tuitionType(course.getTuitionType())
                .summary(course.getSummary())
                .participationTime(course.getParticipationTime())
                .maxParticipants(course.getMaxParticipants())
                .averageRating(course.getAverageRating())
                .viewCounts(course.getViewCounts())
                .build();
    }

    public static Course toCourse(CourseEntity courseEntity){
        return Course.builder()
                .id(courseEntity.getId())
                .bootcampEntity(courseEntity.getBootcampEntity())
                .categoryEntity(courseEntity.getCategoryEntity())
                .subCourseEntity(courseEntity.getSubCourseEntity())
                .name(courseEntity.getName())
                .startDate(courseEntity.getStartDate())
                .endDate(courseEntity.getEndDate())
                .closingDate(courseEntity.getClosingDate())
                .codingTestExempt(courseEntity.isCodingTestExempt())
                .cardRequirement(courseEntity.isCardRequirement())
                .onlineOffline(courseEntity.isOnlineOffline())
                .location(courseEntity.getLocation())
                .tuitionType(courseEntity.getTuitionType())
                .summary(courseEntity.getSummary())
                .participationTime(courseEntity.getParticipationTime())
                .maxParticipants(courseEntity.getMaxParticipants())
                .averageRating(courseEntity.getAverageRating())
                .viewCounts(courseEntity.getViewCounts())
                .build();
    }

    public static List<Course> toCourseList(List<CourseEntity> courseEntities) {
        return courseEntities.stream()
                .map(CourseConverter::toCourse)
                .collect(Collectors.toList());
    }
}