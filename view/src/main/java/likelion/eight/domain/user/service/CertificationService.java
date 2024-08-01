package likelion.eight.domain.user.service;

import likelion.eight.domain.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String email, long userId, String certificationCode){
        //String certificationUrl = generateCertificationUrl(userId, certificationCode); // TODO 이메일 인증 URL 설정
        String title = "인증 코드 발송";
        String content = "인증 코드 : " + certificationCode; //+ certificationUrl;
        mailSender.send(email, title, content);
    }


    private String generateCertificationUrl(long userId, String certificationCode) {
        return "http://localhost:8080/api/users/" + userId + "/verify?certificationCode=" + certificationCode;
    }
}
