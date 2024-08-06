package likelion.eight.domain.user.controller.model;

import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUser {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private String certificationCode;
    private UserStatus userStatus;
    private RoleType roleType;
    private  byte[] image;

}
