package likelion.eight.domain.user.model;

import likelion.eight.common.domain.exception.CertificationCodeNotMatchedException;
import likelion.eight.user.enums.GenderType;
import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final String name;
    private final String address;
    private final String email;
    private final String password;
    private final String nickName;
    private final String phoneNumber;
    private final String certificationCode;
    private final UserStatus userStatus;
    private final GenderType genderType;
    private final RoleType roleType;
    private final Long lastLoginAt;
    private final byte[] image;

    @Builder
    public User(Long id, String name, String address, String email, String password, String nickName, String phoneNumber, String certificationCode, UserStatus userStatus, GenderType genderType, RoleType roleType, Long lastLoginAt, byte[] image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.certificationCode = certificationCode;
        this.userStatus = userStatus;
        this.genderType = genderType;
        this.roleType = roleType;
        this.lastLoginAt = lastLoginAt;
        this.image = image;
    }


    public User certificate(String certificationCode){
        if (!this.certificationCode.equals(certificationCode)) {
            throw new CertificationCodeNotMatchedException();
        }
        return User.builder()
                .id(id)
                .name(name)
                .address(address)
                .email(email)
                .password(password)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .certificationCode(certificationCode)
                .userStatus(UserStatus.ACTIVE)
                .genderType(getGenderType())
                .roleType(roleType)
                .lastLoginAt(lastLoginAt)
                .image(image)
                .build();

    }
}
