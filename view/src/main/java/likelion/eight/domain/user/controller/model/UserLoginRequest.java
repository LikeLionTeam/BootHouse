package likelion.eight.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import likelion.eight.common.annotation.Password;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserLoginRequest {

    @Email
    @NotBlank
    private String email;

    //@Password
    @NotBlank
    private String password;
}
