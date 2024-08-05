package likelion.eight.domain.user.service;

import likelion.eight.domain.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String email, long userId, String certificationCode){
        String certificationUrl = generateCertificationUrl(userId); // TODO 이메일 인증 URL 설정
        String title = "인증 코드 발송";
        String content = "인증코드 : " + certificationCode + " / 링크를 클릭해서 인증코드를 입력 해주세요 : " + certificationUrl;
        mailSender.send(email, title, content);
    }


    private String generateCertificationUrl(long userId) {
        return "http://localhost:8080/users/" + userId + "/verify";
    }
}
