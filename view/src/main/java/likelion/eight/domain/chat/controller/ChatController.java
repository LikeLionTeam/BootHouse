package likelion.eight.domain.chat.controller;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.common.annotation.Login;
import likelion.eight.domain.chat.model.ChatroomResponse;
import likelion.eight.domain.chat.model.ErrorResponse;
import likelion.eight.domain.chat.service.ChatService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.message.MessageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public String chatList(Model model, @Login LoginUser loginUser) {
        log.info("Fetching chat list for user: {}", loginUser.getName());
        List<ChatListEntity> chatList = chatService.getChatList(loginUser.getName());
        model.addAttribute("recentChats", chatList);
        model.addAttribute("username", loginUser.getName());
        return "chat/messages";
    }

    @GetMapping("/{id}")
    public String chatRoom(@PathVariable Long id, Model model, @Login LoginUser loginUser) {
        log.info("Accessing chatroom: {} for user: {}", id, loginUser.getName());
        var chatroom = chatService.getChatroom(id);
        if (!chatService.hasAccess(loginUser.getName(), chatroom)) {
            log.warn("Access denied for user: {} to chatroom: {}", loginUser.getName(), id);
            return "redirect:/messages";
        }
        List<MessageEntity> messages = chatService.getChatroomMessages(id);
        model.addAttribute("chatroom", chatroom);
        model.addAttribute("messages", messages);
        model.addAttribute("username", loginUser.getName());
        return "chat/chatroom";
    }

    @PostMapping("/new")
    public String newChat(@RequestParam String username, @Login LoginUser loginUser) {
        log.info("Creating new chat between: {} and {}", loginUser.getName(), username);
        var chatroom = chatService.createChatroom(Arrays.asList(loginUser.getName(), username));
        return "redirect:/messages/" + chatroom.getId();
    }

    @PostMapping("/startChat")
    @ResponseBody
    public ResponseEntity<?> startChat(@Login LoginUser loginUser, @RequestParam String targetName) {
        log.info("Starting chat: {} with {}", loginUser.getName(), targetName);
        if (loginUser.getName().equals(targetName)) {
            log.warn("User {} attempted to start chat with themselves", loginUser.getName());
            return ResponseEntity.badRequest().body(new ErrorResponse("자기 자신과는 채팅할 수 없습니다."));
        }

        try {
            Long chatroomId = chatService.getOrCreateChatroom(Arrays.asList(loginUser.getName(), targetName));
            log.info("Chat started/resumed in chatroom: {}", chatroomId);
            return ResponseEntity.ok(new ChatroomResponse(chatroomId));
        } catch (RuntimeException e) {
            log.error("Error starting chat: {} with {}", loginUser.getName(), targetName, e);
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}