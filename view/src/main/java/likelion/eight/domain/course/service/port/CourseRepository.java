package likelion.eight.domain.course.service.port;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.controller.model.CourseDto;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
    Course JPA, QueryDSL 관련 코드 모음
 */
public interface CourseRepository {
    // 기존 JPARepository 관련 메서드들
    List<Course> findByClosingDateAfter(LocalDateTime now);

    List<Course> findByOpenCoursesByCategory(Long categoryId);
    Page<Course> findCoursesByFilters(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search,
            Pageable pageable
    );

    Optional<Course> findCourseById(Long courseId);

    Optional<CourseEntity> findByCourseId(Long courseId);
    List<Course> findCourseByBootcampId(Long bootcampId);

    // 추가한 QueryDSL 메서드
    Page<CourseDto> findCoursesByFiltersQueryDSL(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search,
            Pageable pageable);
}