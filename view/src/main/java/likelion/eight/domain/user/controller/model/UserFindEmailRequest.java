package likelion.eight.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import likelion.eight.common.annotation.PhoneNumber;
import lombok.Data;

@Data
public class UserFindEmailRequest {
    @NotBlank
    private String name;
    @NotBlank
    @PhoneNumber
    private String phoneNumber;
}
