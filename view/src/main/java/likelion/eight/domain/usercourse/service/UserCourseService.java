package likelion.eight.domain.usercourse.service;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.usercourse.service.port.UserCourseRepository;
import likelion.eight.user.UserEntity;
import likelion.eight.userCourse.UserCourseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCourseService {

    private final UserCourseRepository userCourseRepository;


    public List<Long> findCourseIdsByUserId(Long userId) {
        return userCourseRepository.findCourseIdsByUserId(userId);
    }

    @Transactional
    public void approveUserToCourse(User user, Course course) {

        // User와 Course를 각각 UserEntity와 CourseEntity로 변환
        UserEntity userEntity = UserConverter.toEntity(user);
        CourseEntity courseEntity = CourseConverter.toEntity(course);

        // 중복 확인: 같은 userEntity와 courseEntity로 이미 UserCourseEntity가 존재하는지 체크
        Optional<UserCourseEntity> existingUserCourse = userCourseRepository.findByUserEntityAndCourseEntity(userEntity, courseEntity);
        if (existingUserCourse.isPresent()) {
            throw new IllegalArgumentException("이미 해당 코스에 등록이 되어 있습니다.");
        }

        // UserCourseEntity를 생성하고 UserEntity와 CourseEntity를 설정
        UserCourseEntity userCourseEntity = UserCourseEntity.builder()
                .userEntity(userEntity)
                .courseEntity(courseEntity)
                .build();


        // UserCourseEntity 저장
        userCourseRepository.save(userCourseEntity);

        // 양방향 관계 설정
        courseEntity.addUser(userCourseEntity);
        userEntity.addCourse(userCourseEntity);

    }
}
