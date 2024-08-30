package likelion.eight.domain.userCourse.service.port;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.userCourse.model.UserCourse;
import likelion.eight.user.UserEntity;
import likelion.eight.userCourse.UserCourseEntity;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository {

    UserCourse save(UserCourseEntity userCourseEntity);

    List<Long> findCourseIdsByUserId(Long userId);

    Optional<UserCourseEntity> findByUserEntityAndCourseEntity(UserEntity userEntity, CourseEntity courseEntity);
}
