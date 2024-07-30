package likelion.eight.domain.user.model;

import likelion.eight.user.enums.GenderType;
import likelion.eight.user.enums.RoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nickName;
    private String phoneNumber;
    private GenderType genderType;
    private RoleType roleType;
    private byte[] image;
}
