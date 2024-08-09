package likelion.eight.domain.chat.service;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.domain.chat.model.ChatMessage;
import likelion.eight.message.MessageEntity;
import likelion.eight.user.UserEntity;
import likelion.eight.chatlist.ifs.ChatListJpaRepository;
import likelion.eight.chatroom.ifs.ChatroomJpaRepository;
import likelion.eight.message.ifs.MessageJpaRepository;
import likelion.eight.user.ifs.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatListJpaRepository chatListRepository;
    private final ChatroomJpaRepository chatroomRepository;
    private final MessageJpaRepository messageRepository;
    private final UserJpaRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatListEntity> getChatList(String name) {
        log.info("Getting chat list for user: {}", name);
        UserEntity user = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatListRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<ChatListEntity> getRecentChats(String username) {
        log.info("Getting recent chats for user: {}", username);
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatListRepository.findByUserOrderByIdDesc(user);
    }

    @Transactional
    public Long getOrCreateChatroom(String name1, String name2) {
        log.info("Getting or creating chatroom for users: {} and {}", name1, name2);
        UserEntity user1 = userRepository.findByName(name1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity user2 = userRepository.findByName(name2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return chatroomRepository.findByUsersContainingAndUsersContaining(user1, user2)
                .map(ChatroomEntity::getId)
                .orElseGet(() -> {
                    ChatroomEntity newChatroom = createChatroom(name1, name2);
                    return newChatroom.getId();
                });
    }

    @Transactional
    public ChatroomEntity createChatroom(String name1, String name2) {
        log.info("Creating chatroom for users: {} and {}", name1, name2);
        UserEntity user1 = userRepository.findByName(name1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity user2 = userRepository.findByName(name2)
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

    @Transactional(readOnly = true)
    public ChatroomEntity getChatroom(Long id) {
        log.info("Getting chatroom with id: {}", id);
        return chatroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
    }

    @Transactional(readOnly = true)
    public boolean hasAccess(String username, ChatroomEntity chatroom) {
        log.info("Checking access for user: {} to chatroom: {}", username, chatroom.getId());
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return chatroom.getUsers().contains(user);
    }

    @Transactional(readOnly = true)
    public List<MessageEntity> getChatroomMessages(Long chatroomId) {
        log.info("Getting messages for chatroom: {}", chatroomId);
        ChatroomEntity chatroom = getChatroom(chatroomId);
        return messageRepository.findByChatroomOrderByIdAsc(chatroom);
    }

    @Transactional
    public MessageEntity saveMessage(ChatMessage chatMessage) {
        log.info("Saving message: {}", chatMessage);
        ChatroomEntity chatroom = chatroomRepository.findById(chatMessage.getChatroomId())
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
        UserEntity sender = userRepository.findByName(chatMessage.getSender())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessageEntity messageEntity = MessageEntity.builder()
                .chatroom(chatroom)
                .sender(sender)
                .message(chatMessage.getMessage())
                .build();

        return messageRepository.save(messageEntity);
    }
}