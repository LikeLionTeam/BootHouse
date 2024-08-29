package likelion.eight.domain.chat.service;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.chatlist.ifs.ChatListJpaRepository;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.chatroom.ifs.ChatroomJpaRepository;
import likelion.eight.domain.chat.infrastructure.ChatRoomRedisRepository;
import likelion.eight.domain.chat.model.ChatMessage;
import likelion.eight.domain.chat.model.ChatRoom;
import likelion.eight.message.MessageEntity;
import likelion.eight.message.ifs.MessageJpaRepository;
import likelion.eight.user.UserEntity;
import likelion.eight.user.ifs.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatListJpaRepository chatListRepository;
    private final ChatroomJpaRepository chatroomRepository;
    private final MessageJpaRepository messageRepository;
    private final UserJpaRepository userRepository;
    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private final ChatRoomRedisRepository chatRoomRedisRepository;


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
    public Long getOrCreateChatroom(List<String> usernames) {
        List<UserEntity> users = userRepository.findAllByNameIn(usernames);
        if (users.size() != usernames.size()) {
            throw new RuntimeException("One or more users not found");
        }

        List<ChatroomEntity> existingChatrooms = chatroomRepository.findByUsersContaining(users, users.size());

        if (!existingChatrooms.isEmpty()) {
            return existingChatrooms.get(0).getId();
        }

        // 기존 채팅방이 없으면 새로 생성
        ChatroomEntity newChatroom = createChatroom(usernames);
        return newChatroom.getId();
    }

    @Transactional
    public ChatroomEntity createChatroom(List<String> usernames) {
        List<UserEntity> users = userRepository.findAllByNameIn(usernames);
        if (users.size() != usernames.size()) {
            throw new RuntimeException("One or more users not found");
        }

        ChatroomEntity chatroom = ChatroomEntity.builder()
                .name(String.join(", ", usernames))
                .build();
        users.forEach(chatroom::addUser);
        chatroomRepository.save(chatroom);

        users.forEach(user -> {
            ChatListEntity chatList = ChatListEntity.builder()
                    .user(user)
                    .chatroom(chatroom)
                    .build();
            chatListRepository.save(chatList);
        });

        // Redis에 채팅방 정보 저장
        ChatRoom redisChatRoom = new ChatRoom();
        redisChatRoom.setId(chatroom.getId().toString());
        redisChatRoom.setName(chatroom.getName());
        redisChatRoom.setUsers(users.stream().map(UserEntity::getName).collect(Collectors.toSet()));
        chatRoomRedisRepository.save(redisChatRoom);

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

        // Redis에 메시지 저장
        String redisKey = "chat:room:" + chatMessage.getChatroomId();
        try {
            redisTemplate.opsForList().rightPush(redisKey, chatMessage);
            log.info("Message saved to Redis. Key: {}", redisKey);
        } catch (Exception e) {
            log.error("Failed to save message to Redis", e);
        }

        // JPA를 통해 DB에 메시지 저장
        MessageEntity savedMessage = messageRepository.save(messageEntity);
        log.info("Message saved to DB. ID: {}", savedMessage.getId());

        return savedMessage;
    }

    // Redis에서 최근 메시지 조회
    public List<ChatMessage> getRecentMessagesFromRedis(Long chatroomId, int count) {
        String redisKey = "chat:room:" + chatroomId;
        return redisTemplate.opsForList().range(redisKey, -count, -1);
    }
}