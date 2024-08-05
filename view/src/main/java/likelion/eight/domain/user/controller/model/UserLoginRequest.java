package likelion.eight.domain.user.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {

    private String email;
    private String password;
}
