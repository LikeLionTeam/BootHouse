package likelion.eight.domain.userauth.controller.model;

import likelion.eight.certificationirequest.enums.AuthRequestType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserAuthCreateRequest {

    private long clientId;
    private AuthRequestType authRequestType;
    private MultipartFile image;

}