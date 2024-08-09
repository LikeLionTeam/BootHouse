package likelion.eight.course.ifs;

import likelion.eight.course.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<CourseEntity, Long>,
        JpaSpecificationExecutor<CourseEntity> {
    List<CourseEntity> findByClosingDateAfter(LocalDateTime now);

    @Query("select c from CourseEntity c where c.categoryEntity.id = :categoryId " +
            "and c.closingDate > CURRENT_TIMESTAMP")
    List<CourseEntity> findByOpenCoursesByCategory(Long categoryId);

    @Query("select c from CourseEntity c " +
            "join fetch c.bootcampEntity " +
            "join fetch c.categoryEntity " +
            "join fetch c.subCourseEntity " +
            "where c.id = :courseId")
    Optional<CourseEntity> findByIdWithBootcamp(Long courseId);
}
