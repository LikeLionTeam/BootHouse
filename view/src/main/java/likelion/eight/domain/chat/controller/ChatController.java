package likelion.eight.domain.chat.controller;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.common.annotation.Login;
import likelion.eight.domain.chat.service.ChatService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.message.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
public class ChatController {
    private final ChatService chatService;

    //    @GetMapping
//    public String chatList(Model model, @Login LoginUser loginUser) {
//        model.addAttribute("chatList", chatService.getChatList(loginUser.getEmail()));
//        return "chat/messages";
//    }
    @GetMapping
    public String chatList(Model model, @Login LoginUser loginUser) {
        List<ChatListEntity> chatList = chatService.getChatList(loginUser.getName());
        model.addAttribute("recentChats", chatList);
        model.addAttribute("username", loginUser.getName());
        return "chat/messages";
    }

//    @GetMapping("/{id}")
//    public String chatRoom(@PathVariable Long id, Model model, @Login LoginUser loginUser) {
//        var chatroom = chatService.getChatroom(id);
//        if (!chatService.hasAccess(loginUser.getName(), chatroom)) {
//            return "redirect:/messages";
//        }
//        model.addAttribute("chatroom", chatroom);
//        model.addAttribute("messages", chatService.getChatroomMessages(id));
//        model.addAttribute("username", loginUser.getName());
//        return "chat/chatroom";
//    }

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

    @PostMapping("/new")
    public String newChat(@RequestParam String username, @Login LoginUser loginUser) {
        var chatroom = chatService.createChatroom(loginUser.getEmail(), username);
        return "redirect:/messages/" + chatroom.getId();
    }

    @PostMapping("/startChat")
    @ResponseBody
    public Object startChat(@Login LoginUser loginUser, @RequestParam String targetName) {
        try {
            Long chatroomId = chatService.getOrCreateChatroom(loginUser.getName(), targetName);
            return new ChatroomResponse(chatroomId);
        } catch (RuntimeException e) {
            return new ErrorResponse(e.getMessage());
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