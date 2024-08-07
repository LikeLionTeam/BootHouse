package likelion.eight.likeCourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeCourseJpaRepository extends JpaRepository<LikeCourseEntity,Long> {

    @Query("select lc from LikeCourseEntity lc where lc.userEntity.id = :userId and lc.courseEntity.id = :courseId")
    Optional<LikeCourseEntity> findByUserIdAndCourseId(Long userId, Long courseId);
}
