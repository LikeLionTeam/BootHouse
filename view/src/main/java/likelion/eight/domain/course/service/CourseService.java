package likelion.eight.domain.course.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getOpenCourses(){
        LocalDateTime now = LocalDateTime.now();
        List<Course> courses = courseRepository.findByClosingDateAfter(now);
        return Optional.ofNullable(courses)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No open courses found."));
    }

    public List<Course> findByOpenCoursesByCategory(Long categoryId){
        List<Course> courses = courseRepository.findByOpenCoursesByCategory(categoryId);
        return Optional.ofNullable(courses)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No open courses found for category ID: " + categoryId));
    }

    public List<Course> findCoursesByFilters(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search
    ){
        List<Course> coursesByFilters = courseRepository.findCoursesByFilters(categoryId, courseFilter, sort, search);
        return Optional.ofNullable(coursesByFilters)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No course exist by that filter"));
    }
}
