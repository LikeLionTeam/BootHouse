package likelion.eight.domain.user.converter;

import likelion.eight.common.service.port.UuidHolder;
import likelion.eight.domain.user.controller.model.UserCreateRequest;
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
                .nickName(user.getNickName())
                .phoneNumber(user.getPhoneNumber())
                .genderType(user.getGenderType())
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
                .nickName(userEntity.getNickName())
                .phoneNumber(userEntity.getPhoneNumber())
                .address(userEntity.getAddress())
                .lastLoginAt(userEntity.getLastLoginAt())
                .userStatus(userEntity.getUserStatus())
                .certificationCode(userEntity.getCertificationCode())
                .genderType(userEntity.getGenderType())
                .image(userEntity.getImage())
                .roleType(userEntity.getRoleType())
                .build();
    }

    public static User toUser(UserCreateRequest userCreateRequest, UuidHolder uuidHolder){
        return User.builder()
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .nickName(userCreateRequest.getNickName())
                .phoneNumber(userCreateRequest.getPhoneNumber())
                .address(userCreateRequest.getAddress())
                .roleType(RoleType.USER)
                .certificationCode(uuidHolder.random())
                .userStatus(UserStatus.PENDING)
                .genderType(userCreateRequest.getGenderType())
                .build();
    }
}
