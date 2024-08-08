package likelion.eight.likeCourse;

import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeCourseJpaRepository extends JpaRepository<LikeCourseEntity,Long> {

   List<LikeCourseEntity> findAllByUserEntity(UserEntity userEntity);

    @Query("select lc from LikeCourseEntity lc where lc.userEntity.id = :userId and lc.courseEntity.id = :courseId")
    Optional<LikeCourseEntity> findByUserIdAndCourseId(Long userId, Long courseId);
}
