package likelion.eight.domain.userauth.service;

import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.domain.usercourse.service.UserCourseService;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.converter.UserAuthConverter;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.port.UserAuthRepository;
import likelion.eight.user.enums.RoleType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

//@Service
@Builder
@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UserAuthS3Service {

    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;
    private final S3Service s3ServiceImpl;
    private final ClockHolder clockHolder;
    private final CourseRepository courseRepository;
    private final UserCourseService userCourseService;

    public UserAuth create(UserAuthCreateRequest request) {
        User client = userRepository.getById(request.getClientId());
        String imageUrl = s3ServiceImpl.saveFile(request.getImage());
        Optional<CourseEntity> courseEntityOptional = courseRepository.findByCourseId(request.getCourseId());
        // Optional이 비어 있는 경우 예외를 던지기
        CourseEntity courseEntity = courseEntityOptional.orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + request.getCourseId()));
        // CourseEntity를 Course로 변환
        Course course = CourseConverter.toCourse(courseEntity);

        UserAuth userAuth = UserAuthConverter.toUserAuth(client, course, request, imageUrl);

        return userAuthRepository.save(userAuth);
    }

    @Transactional(readOnly = true)
    public Page<UserAuth> getUserAuthPage(int page, int size, LoginUser loginUser) {

        if (!Objects.equals(loginUser.getRoleType(), RoleType.ADMIN)) {
            throw new CertificationFailedException("관리자만 접근 할 수 있습니다.");
        }

        return userAuthRepository.findAll(page, size);
    }

    public void delete(long id) {
        UserAuth userAuth = userAuthRepository.getById(id);
        s3ServiceImpl.deleteFile(userAuth.getImageUrl());
        userAuthRepository.delete(userAuth);
    }

    public void approve(long id, long courseId) {
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.approve(clockHolder);

        User user = userAuth.getUser();
        user = user.changeRoleType(userAuth.getAuthRequestType());

        userAuthRepository.save(userAuth);
        userRepository.save(user);   // UserEntity 저장 (UserCourseEntity도 함께 저장)

        // course랑 유저를 이어줘야 함(코스아이디 필요)
        CourseEntity courseEntity = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));
        Course course = CourseConverter.toCourse(courseEntity);


        userCourseService.approveUserToCourse(user, course);
    }

    public void deny(long id) {
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.deny(clockHolder);

        userAuthRepository.save(userAuth);
    }

}