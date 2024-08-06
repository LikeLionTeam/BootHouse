package likelion.eight.domain.user.converter;

import likelion.eight.common.service.port.UuidHolder;
import likelion.eight.domain.user.controller.model.UserCreateRequest;
import likelion.eight.domain.user.controller.model.UserResponse;
import likelion.eight.domain.user.model.User;
import likelion.eight.user.UserEntity;
import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;

public class UserConverter {

    public static UserEntity toEntity(User user){
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .roleType(user.getRoleType())
                .address(user.getAddress())
                .lastLoginAt(user.getLastLoginAt())
                .userStatus(user.getUserStatus())
                .certificationCode(user.getCertificationCode())
                .image(user.getImage())
                .build();
    }

    public static User toUser(UserEntity userEntity){
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phoneNumber(userEntity.getPhoneNumber())
                .address(userEntity.getAddress())
                .lastLoginAt(userEntity.getLastLoginAt())
                .userStatus(userEntity.getUserStatus())
                .certificationCode(userEntity.getCertificationCode())
                .image(userEntity.getImage())
                .roleType(userEntity.getRoleType())
                .build();
    }

    public static User toUser(UserCreateRequest userCreateRequest, UuidHolder uuidHolder){
        return User.builder()
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .phoneNumber(userCreateRequest.getPhoneNumber())
                .address(userCreateRequest.getAddress())
                .roleType(RoleType.USER) // TODO 어드민권한주기
                .certificationCode(uuidHolder.random())
                .userStatus(UserStatus.PENDING)
                .build();
    }

    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .certificationCode(user.getCertificationCode())
                .userStatus(user.getUserStatus())
                .roleType(user.getRoleType())
                .image(user.getImage())
                .build();
    }
}
