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

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {
        log.info("User added to chat: {}", chatMessage.getSender());
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatMessage.setMessage(chatMessage.getSender() + " 님이 채팅방에 입장했습니다.");
        messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getChatroomId(), chatMessage);
    }

    @MessageMapping("/chat.leaveUser")
    public void removeUser(@Payload ChatMessage chatMessage) {
        log.info("User left chat: {}", chatMessage.getSender());
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        chatMessage.setMessage(chatMessage.getSender() + " 님이 채팅방을 나갔습니다.");
        messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getChatroomId(), chatMessage);
    }
}