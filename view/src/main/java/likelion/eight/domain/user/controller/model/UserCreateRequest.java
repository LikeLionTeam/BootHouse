package likelion.eight.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import likelion.eight.common.annotation.Password;
import likelion.eight.common.annotation.PhoneNumber;
import likelion.eight.user.enums.GenderType;
import likelion.eight.user.enums.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @Password
    @NotBlank
    private String password;
    @NotBlank
    private String nickName;
    @NotBlank
    private String address;
    @NotBlank
    @PhoneNumber
    private String phoneNumber;
    @NotNull
    private GenderType genderType;
}
