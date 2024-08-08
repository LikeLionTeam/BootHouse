package likelion.eight.domain.user.controller.model;

import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditRequest {

    private Long id;
    private String address;
    private String phoneNumber;
    private UserStatus userStatus;
    private RoleType roleType;
    private  byte[] image;

}
