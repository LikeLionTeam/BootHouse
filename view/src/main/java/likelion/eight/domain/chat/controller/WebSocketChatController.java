package likelion.eight.domain.chat.controller;

import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.domain.chat.model.ChatMessage;
import likelion.eight.domain.chat.service.ChatService;
import likelion.eight.domain.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final TokenService tokenService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        // 토큰 검증 로직 추가
        try {
            Long userId = tokenService.validationToken(chatMessage.getToken());
            if (userId != null) {
                chatService.saveMessage(chatMessage);
                messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getChatroomId(), chatMessage);
            }
        } catch (CertificationFailedException e) {
            // 토큰 만료 또는 인증 실패 시 클라이언트에게 에러 메시지 전송
            messagingTemplate.convertAndSendToUser(chatMessage.getSender(), "/queue/errors", "Token expired or invalid. Please refresh the page.");
        }
    }
}