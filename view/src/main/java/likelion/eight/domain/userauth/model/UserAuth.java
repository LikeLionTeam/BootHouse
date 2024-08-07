package likelion.eight.domain.userauth.model;

import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.certificationirequest.enums.AuthRequestType;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class UserAuth {
    private final Long id;
    private final User user;
    private final  AuthRequestType authRequestType;
    private final  AuthRequestStatus authRequestStatus;
    private final  LocalDateTime determined_at;
    private final  byte[] image;
    private final String imageUrl;

    @Builder
    public UserAuth(Long id, User user, AuthRequestType authRequestType, AuthRequestStatus authRequestStatus, LocalDateTime determined_at, byte[] image, String imageUrl) {
        this.id = id;
        this.user = user;
        this.authRequestType = authRequestType;
        this.authRequestStatus = authRequestStatus;
        this.determined_at = determined_at;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public UserAuth approve(ClockHolder clockHolder){
        return UserAuth.builder()
                .id(id)
                .user(user)
                .authRequestType(authRequestType)
                .authRequestStatus(AuthRequestStatus.APPROVAL)
                .determined_at(clockHolder.now())
                .image(image)
                .imageUrl(imageUrl)
                .build();
    }

    public UserAuth deny(ClockHolder clockHolder){
        return UserAuth.builder()
                .id(id)
                .user(user)
                .authRequestType(authRequestType)
                .authRequestStatus(AuthRequestStatus.DENY)
                .determined_at(clockHolder.now())
                .image(image)
                .imageUrl(imageUrl)
                .build();
    }
}
