package likelion.eight.domain.course.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseCustomRepositoryImpl implements CourseCustomRepository {
    @PersistenceContext
    private EntityManager em;

    // 모집순 + 최대 4개 Course 반환
    @Override
    public List<Course> findTop4ByOrderByStartDateDesc() {
        List<CourseEntity> courseEntities = em.createQuery("select c from CourseEntity c order by c.startDate desc", CourseEntity.class)
                .setMaxResults(4)
                .getResultList();

        return courseEntities.stream()
                .map(CourseConverter::toCourse)
                .collect(Collectors.toList());
    }
}
