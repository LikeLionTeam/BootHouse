package likelion.eight.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import likelion.eight.common.annotation.PhoneNumber;
import lombok.Data;

@Data
public class UserFindPasswordRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @PhoneNumber
    private String phoneNumber;
}
