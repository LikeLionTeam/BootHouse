package likelion.eight.domain.chat.service;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.chatlist.ifs.ChatListJpaRepository;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.chatroom.ifs.ChatroomJpaRepository;
import likelion.eight.domain.chat.model.ChatMessage;
import likelion.eight.domain.token.service.TokenService;
import likelion.eight.message.MessageEntity;
import likelion.eight.message.ifs.MessageJpaRepository;
import likelion.eight.user.UserEntity;
import likelion.eight.user.ifs.UserJpaRepository;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatListJpaRepository chatListRepository;
    private final ChatroomJpaRepository chatroomRepository;
    private final MessageJpaRepository messageRepository;
    private final UserJpaRepository userRepository;
    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public List<ChatListEntity> getChatList(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatListRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public ChatroomEntity getChatroom(Long id) {
        return chatroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
    }

    @Transactional(readOnly = true)
    public boolean hasAccess(String username, ChatroomEntity chatroom) {
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatroom.getUsers().contains(user);
    }

    @Transactional
    public ChatroomEntity createChatroom(String email1, String email2) {
        UserEntity user1 = userRepository.findByEmail(email1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity user2 = userRepository.findByEmail(email2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatroomEntity chatroom = ChatroomEntity.builder()
                .name(user1.getName() + ", " + user2.getName())
                .build();
        chatroom.addUser(user1);
        chatroom.addUser(user2);
        chatroomRepository.save(chatroom);

        ChatListEntity chatList1 = ChatListEntity.builder()
                .user(user1)
                .chatroom(chatroom)
                .build();
        ChatListEntity chatList2 = ChatListEntity.builder()
                .user(user2)
                .chatroom(chatroom)
                .build();
        chatListRepository.saveAll(Arrays.asList(chatList1, chatList2));

        return chatroom;
    }

    @Transactional
    public MessageEntity saveMessage(ChatMessage chatMessage) {
        Long userId = tokenService.validationToken(chatMessage.getToken());
        ChatroomEntity chatroom = chatroomRepository.findById(chatMessage.getChatroomId())
                .orElseThrow(() -> new ResourceNotFoundException("Chatroom", chatMessage.getChatroomId()));
        UserEntity sender = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        MessageEntity messageEntity = MessageEntity.builder()
                .chatroom(chatroom)
                .sender(sender)
                .message(chatMessage.getMessage())
                .build();

        return messageRepository.save(messageEntity);
    }

    @Transactional(readOnly = true)
    public List<MessageEntity> getChatroomMessages(Long chatroomId) {
        ChatroomEntity chatroom = getChatroom(chatroomId);
        return messageRepository.findByChatroomOrderByIdAsc(chatroom);
    }

    @Transactional(readOnly = true)
    public List<ChatListEntity> getRecentChats(String username) {
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatListRepository.findByUserOrderByIdDesc(user);
    }

    @Transactional
    public Long getOrCreateChatroom(String name1, String name2) {
        UserEntity user1 = userRepository.findByName(name1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity user2 = userRepository.findByName(name2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return chatroomRepository.findByUsersContainingAndUsersContaining(user1, user2)
                .map(ChatroomEntity::getId)
                .orElseGet(() -> {
                    ChatroomEntity newChatroom = ChatroomEntity.builder()
                            .name(name1 + ", " + name2)
                            .build();
                    newChatroom.addUser(user1);
                    newChatroom.addUser(user2);
                    chatroomRepository.save(newChatroom);

                    ChatListEntity chatList1 = ChatListEntity.builder()
                            .user(user1)
                            .chatroom(newChatroom)
                            .build();
                    ChatListEntity chatList2 = ChatListEntity.builder()
                            .user(user2)
                            .chatroom(newChatroom)
                            .build();
                    chatListRepository.saveAll(Arrays.asList(chatList1, chatList2));

                    return newChatroom.getId();
                });
    }
}