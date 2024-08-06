package likelion.eight.course.ifs;

import likelion.eight.course.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long>,
        JpaSpecificationExecutor<CourseEntity> {
    List<CourseEntity> findByClosingDateAfter(LocalDateTime now);

    @Query("select c from CourseEntity c where c.categoryEntity.id = :categoryId " +
            "and c.closingDate > CURRENT_TIMESTAMP")
    List<CourseEntity> findByOpenCoursesByCategory(Long categoryId);
}
