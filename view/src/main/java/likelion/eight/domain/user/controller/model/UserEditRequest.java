package likelion.eight.domain.user.controller.model;

import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditRequest {

    private String password;
    private String address;
    private String phoneNumber;

}
