package likelion.eight.domain.course.service.port;

import likelion.eight.domain.course.model.Course;

import java.util.List;

public interface CourseCustomRepository {
    List<Course> findTop4ByOrderByStartDateDesc();
}
