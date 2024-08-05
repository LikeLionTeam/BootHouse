package likelion.eight.domain.subcourse.service.port;

import likelion.eight.domain.subcourse.model.SubCourse;

import java.util.List;

public interface SubCourseRepository {
    List<SubCourse> findSubCourseByCategoryId(Long categoryId);
}
