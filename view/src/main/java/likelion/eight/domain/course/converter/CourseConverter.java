package likelion.eight.domain.course.converter;

import likelion.eight.category.CategoryEntity;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.bootcamp.converter.BootcampConverter;
import likelion.eight.domain.category.converter.CategoryConverter;
import likelion.eight.domain.category.model.Category;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.subcourse.converter.SubCourseConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseConverter {
    public static CourseEntity toEntity(Course course){
        return CourseEntity.builder()
                .id(course.getId())
                .bootcampEntity(BootcampConverter.toEntity(course.getBootcamp()))
                .categoryEntity(CategoryConverter.toEntity(course.getCategory()))
                .subCourseEntity(SubCourseConverter.toEntity(course.getSubCourse()))
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
                .build();
    }

    public static Course toCourse(CourseEntity courseEntity){
        if (courseEntity == null){
            throw new ResourceNotFoundException("CourseEntity가 null입니다.");
        }

        return Course.builder()
                .id(courseEntity.getId())
                .bootcamp(BootcampConverter.toBootcamp(courseEntity.getBootcampEntity()))
                .category(CategoryConverter.toCategory(courseEntity.getCategoryEntity()))
                .subCourse(SubCourseConverter.toSubcourse(courseEntity.getSubCourseEntity()))
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
                .build();
    }

    public static List<Course> toCourseList(List<CourseEntity> courseEntities) {
        return courseEntities.stream()
                .map(CourseConverter::toCourse)
                .collect(Collectors.toList());
    }

    public static Optional<Course> toCourseOptional(Optional<CourseEntity> courseEntity){
        return courseEntity.map(CourseConverter::toCourse);
    }

}