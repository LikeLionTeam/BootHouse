package likelion.eight.domain.chat.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.chat.service.ChatService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.UserService;
import likelion.eight.message.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /**
     * 모든 사용자 목록을 조회합니다.
     *
     * @return 모든 사용자 목록
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * 채팅방에 새로운 사용자를 초대합니다.
     *
     * @param chatroomId 채팅방 ID
     * @param userIds 초대할 사용자 ID 목록
     * @param loginUser 현재 로그인한 사용자 정보
     * @return 초대 결과 메시지 또는 에러 메시지
     */
    @PostMapping("/invite")
    public ResponseEntity<?> inviteUsers(@RequestParam Long chatroomId, @RequestBody List<Long> userIds, @Login LoginUser loginUser) {
        try {
            chatService.inviteUsers(chatroomId, userIds);
            return ResponseEntity.ok().body(Map.of("message", "Users invited successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }


    @GetMapping("/{chatroomId}/messages")
    public ResponseEntity<List<MessageEntity>> getChatroomMessages(@PathVariable Long chatroomId, @Login LoginUser loginUser) {
        List<MessageEntity> messages = chatService.getChatroomMessages(chatroomId);
        return ResponseEntity.ok(messages);
    }

    /**
     * 사용자가 채팅방을 나갑니다.
     *
     * @param chatroomId 나갈 채팅방 ID
     * @param loginUser 현재 로그인한 사용자 정보
     * @return 채팅방 나가기 결과
     */
    @PostMapping("/{chatroomId}/leave")
    public ResponseEntity<?> leaveChat(@PathVariable Long chatroomId, @Login LoginUser loginUser) {
        try {
            chatService.leaveChat(chatroomId, loginUser.getId());
            return ResponseEntity.ok().body(Map.of("success", true, "message", "Successfully left the chat"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }

    /**
     * 채팅방의 마지막 메시지 정보를 조회합니다.
     *
     * @param chatroomId 조회할 채팅방 ID
     * @return 마지막 메시지 정보
     */
    @GetMapping("/{chatroomId}/last-message")
    public ResponseEntity<Map<String, Object>> getLastMessageInfo(@PathVariable Long chatroomId) {
        return ResponseEntity.ok(chatService.getLastMessageInfo(chatroomId));
    }



}