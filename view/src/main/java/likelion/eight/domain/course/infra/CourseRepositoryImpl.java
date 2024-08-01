package likelion.eight.domain.course.infra;

import likelion.eight.course.CourseEntity;
import likelion.eight.course.ifs.CourseJpaRepository;
import likelion.eight.domain.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Optional<CourseEntity> findById(Long id) {
        return courseJpaRepository.findById(id);
    }

    @Override
    public CourseEntity save(CourseEntity courseEntity) {
        return courseJpaRepository.save(courseEntity);
    }

    @Override
    public void deleteById(Long id) {
        courseJpaRepository.deleteById(id);
    }

    @Override
    public List<CourseEntity> findAll() {
        return courseJpaRepository.findAll();
    }
}
