package likelion.eight.domain.course.service;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<CourseEntity> findAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CourseEntity findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }

    @Transactional
    public CourseEntity saveCourse(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
