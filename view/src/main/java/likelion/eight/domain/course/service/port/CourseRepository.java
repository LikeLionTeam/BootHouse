package likelion.eight.domain.course.service.port;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.model.Course;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository {
    List<Course> findByClosingDateAfter(LocalDateTime now);

    List<Course> findByOpenCoursesByCategory(Long categoryId);

    List<Course> findCoursesByFilters(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search
    );
}