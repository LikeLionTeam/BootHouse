package likelion.eight.SubCourseEntity.ifs;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCourseJpaRepository extends JpaRepository<SubCourseEntity, Long> {
    SubCourseEntity findByName(String name);
}
