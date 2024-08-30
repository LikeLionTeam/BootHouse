package likelion.eight.domain.userCourse.converter;

import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.userCourse.model.UserCourse;
import likelion.eight.userCourse.UserCourseEntity;

public class UserCourseConverter {

    public static UserCourseEntity toEntity(UserCourse userCourse, Course course, User user) {

        return UserCourseEntity.builder()
                .id(userCourse.getId())
                .userEntity(UserConverter.toEntity(user))
                .courseEntity(CourseConverter.toEntity(course))
                .build();
    }

    public static UserCourse toUserCourse(UserCourseEntity userCourseEntity) {
        return UserCourse.builder()
                .id(userCourseEntity.getId())
                .courseId(userCourseEntity.getCourseEntity().getId())
                .userId(userCourseEntity.getUserEntity().getId())
                .build();
    }
}
