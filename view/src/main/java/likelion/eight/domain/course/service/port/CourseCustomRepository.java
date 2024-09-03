package likelion.eight.domain.course.service.port;

import likelion.eight.domain.course.model.Course;

import java.util.List;
/*
    Course 커스텀 JPA 쿼리
 */
public interface CourseCustomRepository {
    List<Course> findTop4ByOrderByStartDateDesc();
}
