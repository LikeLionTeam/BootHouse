package likelion.eight.domain.chat.controller;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.common.annotation.Login;
import likelion.eight.domain.chat.service.ChatService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.message.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
public class ChatController {
    private final ChatService chatService;


    // lobby
    @GetMapping
    public String chatList(Model model, @Login LoginUser loginUser) {
        List<ChatListEntity> chatList = chatService.getChatList(loginUser.getName());
        model.addAttribute("recentChats", chatList);
        model.addAttribute("username", loginUser.getName());
        return "chat/messages";
    }

    // 특정 인원과 대화
    @GetMapping("/{id}")
    public String chatRoom(@PathVariable Long id, Model model, @Login LoginUser loginUser) {
        var chatroom = chatService.getChatroom(id);
        if (!chatService.hasAccess(loginUser.getName(), chatroom)) {
            return "redirect:/messages";
        }
        List<MessageEntity> messages = chatService.getChatroomMessages(id);
        model.addAttribute("chatroom", chatroom);
        model.addAttribute("messages", messages);
        model.addAttribute("username", loginUser.getName());
        return "chat/chatroom";
    }

    // 새로 생성
    @PostMapping("/new")
    public String newChat(@RequestParam String username, @Login LoginUser loginUser) {
        var chatroom = chatService.createChatroom(loginUser.getName(), username);
        return "redirect:/messages/" + chatroom.getId();
    }

    // 대화 시작
    @PostMapping("/startChat")
    @ResponseBody
    public ResponseEntity<?> startChat(@Login LoginUser loginUser, @RequestParam String targetName) {
        if (loginUser.getName().equals(targetName)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("자기 자신과는 채팅할 수 없습니다."));
        }

        try {
            Long chatroomId = chatService.getOrCreateChatroom(loginUser.getName(), targetName);
            return ResponseEntity.ok(new ChatroomResponse(chatroomId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    private static class ChatroomResponse {
        public Long chatroomId;

        ChatroomResponse(Long chatroomId) {
            this.chatroomId = chatroomId;
        }
    }

    private static class ErrorResponse {
        public String error;

        ErrorResponse(String error) {
            this.error = error;
        }
    }
}