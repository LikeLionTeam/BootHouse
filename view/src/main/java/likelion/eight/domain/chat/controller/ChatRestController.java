package likelion.eight.domain.chat.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.chat.service.ChatService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteUsers(@RequestParam Long chatroomId, @RequestBody List<Long> userIds, @Login LoginUser loginUser) {
        try {
            chatService.inviteUsers(chatroomId, userIds);
            return ResponseEntity.ok().body(Map.of("message", "Users invited successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{chatroomId}/leave")
    public ResponseEntity<?> leaveChat(@PathVariable Long chatroomId, @Login LoginUser loginUser) {
        chatService.leaveChat(chatroomId, loginUser.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{chatroomId}/last-message")
    public ResponseEntity<Map<String, Object>> getLastMessageInfo(@PathVariable Long chatroomId) {
        return ResponseEntity.ok(chatService.getLastMessageInfo(chatroomId));
    }
}