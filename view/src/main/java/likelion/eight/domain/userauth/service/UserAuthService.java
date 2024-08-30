package likelion.eight.domain.userauth.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.converter.UserAuthConverter;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.port.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
//@Service
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;
    private final CourseRepository courseRepository;

    public UserAuth create(UserAuthCreateRequest request){
        User client = userRepository.getById(request.getClientId());

        Optional<CourseEntity> courseEntityOptional = courseRepository.findByCourseId(request.getCourseId());
        // Optional이 비어 있는 경우 예외를 던지기
        CourseEntity courseEntity = courseEntityOptional.orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + request.getCourseId()));
        // CourseEntity를 Course로 변환
        Course course = CourseConverter.toCourse(courseEntity);

        try{
            UserAuth userAuth = UserAuthConverter.toUserAuth(client, course, request);
            userAuthRepository.save(userAuth);
            return userAuth;
        }catch (IOException e){
            throw new IllegalArgumentException();
        }
    }

/*    @Transactional(readOnly = true)
    public byte[] getUserAuthImage(long id){
        UserAuth userAuth = userAuthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAuth", id));

        return Optional.ofNullable(userAuth.getImage())
                .map(it -> {return userAuth.getImage();})
                .orElseThrow(() -> new ResourceNotFoundException("AuthImage", id));
    }*/

    @Transactional(readOnly = true)
    public Page<UserAuth> getUserAuthPage(int page, int size){
        return userAuthRepository.findAll(page, size);
    }

    public void approve(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.approve(clockHolder);
        userAuthRepository.save(userAuth);
    }

    public void deny(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.deny(clockHolder);
        userAuthRepository.save(userAuth);
    }
}
