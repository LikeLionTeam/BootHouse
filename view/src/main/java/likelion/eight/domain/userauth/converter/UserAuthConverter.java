package likelion.eight.domain.userauth.converter;

import likelion.eight.certificationirequest.UserAuthEntity;
import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.model.UserAuth;

import java.io.IOException;

public class UserAuthConverter {

    public static UserAuthEntity toEntity(UserAuth userAuth){
        return UserAuthEntity.builder()
                .id(userAuth.getId())
                .userEntity(UserConverter.toEntity(userAuth.getUser()))
                .courseEntity(CourseConverter.toEntity(userAuth.getCourse())) // courseEntity 필드 추가
                .authRequestType(userAuth.getAuthRequestType())
                .authRequestStatus(userAuth.getAuthRequestStatus())
                .determined_at(userAuth.getDetermined_at())
                .image(userAuth.getImage())
                .imageUrl(userAuth.getImageUrl())
                .build();
    }

    public static UserAuth toUserAuth(UserAuthEntity userAuthEntity){
        return UserAuth.builder()
                .id(userAuthEntity.getId())
                .user(UserConverter.toUser(userAuthEntity.getUserEntity()))
                .course(CourseConverter.toCourse(userAuthEntity.getCourseEntity())) // Course 필드 추가
                .authRequestType(userAuthEntity.getAuthRequestType())
                .authRequestStatus(userAuthEntity.getAuthRequestStatus())
                .determined_at(userAuthEntity.getDetermined_at())
                .image(userAuthEntity.getImage())
                .imageUrl(userAuthEntity.getImageUrl())
                .build();
    }


    public static UserAuth toUserAuth(User user, Course course, UserAuthCreateRequest request, String imageUrl){
        return UserAuth.builder()
                .user(user)
                .course(course)
                .authRequestType(request.getAuthRequestType())
                .authRequestStatus(AuthRequestStatus.PENDING)
                .imageUrl(imageUrl)
                .build();
    }

    public static UserAuth toUserAuth(User user, Course course ,UserAuthCreateRequest request) throws IOException {
        return UserAuth.builder()
                .user(user)
                .course(course)
                .authRequestType(request.getAuthRequestType())
                .authRequestStatus(AuthRequestStatus.PENDING)
                .image(request.getImage().getBytes())
                .build();
    }

}
