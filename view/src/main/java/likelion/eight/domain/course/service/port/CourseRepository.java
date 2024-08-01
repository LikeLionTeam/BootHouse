package likelion.eight.domain.course.service.port;

import likelion.eight.course.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Optional<CourseEntity> findById(Long id);
    CourseEntity save(CourseEntity courseEntity);
    void deleteById(Long id);
    List<CourseEntity> findAll();

}
