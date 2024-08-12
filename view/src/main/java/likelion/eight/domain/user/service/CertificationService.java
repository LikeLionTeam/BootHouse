package likelion.eight.domain.user.service;

import likelion.eight.domain.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void sendCode(String email, String certificationCode){
        String title = "부트하우스 인증 코드 발송";
        String content = "인증코드 : " + certificationCode;
        mailSender.send(email, title, content);
    }

    public void sendPassword(String email, String password){
        String title = "부트하우스 비밀번호 전송";
        String content = "비밀번호 : " + password;
        mailSender.send(email, title, content);
    }
}
