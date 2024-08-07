package likelion.eight.domain.userauth.converter;

import likelion.eight.certificationirequest.UserAuthEntity;
import likelion.eight.certificationirequest.enums.AuthRequestStatus;
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
                .authRequestType(userAuthEntity.getAuthRequestType())
                .authRequestStatus(userAuthEntity.getAuthRequestStatus())
                .determined_at(userAuthEntity.getDetermined_at())
                .image(userAuthEntity.getImage())
                .imageUrl(userAuthEntity.getImageUrl())
                .build();
    }

    public static UserAuth toUserAuth(User user, UserAuthCreateRequest request) throws IOException {
        return UserAuth.builder()
                .user(user)
                .authRequestType(request.getAuthRequestType())
                .authRequestStatus(AuthRequestStatus.PENDING)
                .image(request.getImage().getBytes())
                .build();
    }
}
