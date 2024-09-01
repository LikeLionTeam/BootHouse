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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
@Slf4j
public class ChatController {
    private final ChatService chatService;

    /**
     * 사용자의 채팅 목록을 조회합니다.
     *
     * @param model Model 객체
     * @param loginUser 로그인한 사용자 정보
     * @return 채팅 목록 페이지
     */
    @GetMapping
    public String chatList(Model model, @Login LoginUser loginUser) {
        log.info("Fetching chat list for user: {}", loginUser.getName());
        List<ChatListEntity> chatList = chatService.getChatList(loginUser.getName());
        model.addAttribute("recentChats", chatList);
        model.addAttribute("username", loginUser.getName());
        log.info("Chat list fetched successfully for user: {}", loginUser.getName());
        return "chat/messages";
    }


    /**
     * 특정 채팅방을 조회합니다.
     *
     * @param id 채팅방 ID
     * @param model Model 객체
     * @param loginUser 로그인한 사용자 정보
     * @return 채팅방 페이지 또는 리다이렉트 URL
     */
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
        log.info("Chatroom {} accessed successfully by user: {}", id, loginUser.getName());
        return "chat/chatroom";
    }

    /**
     * 새로운 채팅방을 생성합니다.
     *
     * @param userIds 채팅방에 포함될 사용자 ID 목록
     * @param loginUser 로그인한 사용자 정보
     * @return 생성된 채팅방 정보 또는 에러 메시지
     */
    @PostMapping("/new")
    public ResponseEntity<?> newChat(@RequestBody List<Long> userIds, @Login LoginUser loginUser) {
        log.info("Creating new chat for users: {}", userIds);
        userIds.add(loginUser.getId()); // 현재 로그인한 사용자도 포함
        try {
            var chatroom = chatService.createChatroomWithUserIds(userIds);
            log.info("Chatroom created successfully. ID: {}", chatroom.getId());
            return ResponseEntity.ok(Map.of("chatroomId", chatroom.getId()));
        } catch (Exception e) {
            log.error("Error creating chatroom", e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 채팅을 시작하거나 기존 채팅방을 조회합니다.
     *
     * @param loginUser 로그인한 사용자 정보
     * @param targetName 대화 상대방 이름
     * @return 채팅방 정보 또는 에러 메시지
     */
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