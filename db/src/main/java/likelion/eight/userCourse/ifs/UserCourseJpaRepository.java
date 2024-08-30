package likelion.eight.userCourse.ifs;

import likelion.eight.course.CourseEntity;
import likelion.eight.user.UserEntity;
import likelion.eight.userCourse.UserCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseJpaRepository extends JpaRepository<UserCourseEntity, Long> {
    List<UserCourseEntity> findByUserEntity_Id(Long userId);

    Optional<UserCourseEntity> findByUserEntityAndCourseEntity(UserEntity userEntity, CourseEntity courseEntity);

}
