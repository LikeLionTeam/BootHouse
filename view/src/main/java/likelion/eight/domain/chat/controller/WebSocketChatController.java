package likelion.eight.domain.chat.controller;


import likelion.eight.domain.chat.model.ChatMessage;
import likelion.eight.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        log.info("Received message: {}", chatMessage);
        chatMessage.setTimestamp(System.currentTimeMillis());
        chatService.saveMessage(chatMessage);
        messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getChatroomId(), chatMessage);
        log.info("Message sent to topic: /topic/messages/{}", chatMessage.getChatroomId());
    }
}