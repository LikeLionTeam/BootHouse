package likelion.eight.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import likelion.eight.common.annotation.Password;
import likelion.eight.common.annotation.PhoneNumber;
import lombok.Builder;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    //@Password //TODO
    @NotBlank
    private String password;
    @NotBlank
    private String address;
    @NotBlank
    //@PhoneNumber
    private String phoneNumber;
}
