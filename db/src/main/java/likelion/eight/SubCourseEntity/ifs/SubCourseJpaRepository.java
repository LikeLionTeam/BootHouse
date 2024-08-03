package likelion.eight.SubCourseEntity.ifs;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCourseJpaRepository extends JpaRepository<SubCourseEntity, Long> {
    SubCourseEntity findByName(String name);

    @Query("select sc from SubCourseEntity sc where sc.categoryEntity.id = :categoryId")
    List<SubCourseEntity> findSubCourseByCategoryId(Long categoryId);
}
