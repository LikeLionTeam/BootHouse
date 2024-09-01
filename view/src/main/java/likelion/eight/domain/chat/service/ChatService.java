package likelion.eight.domain.chat.service;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.chatlist.ifs.ChatListJpaRepository;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.chatroom.ifs.ChatroomJpaRepository;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
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

import java.time.LocalDateTime;
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

    /**
     * 사용자의 채팅 목록을 조회합니다.
     * 자기 자신만 있는 채팅방은 제외됩니다.
     *
     * @param name 사용자 이름
     * @return 채팅 목록
     */
    @Transactional(readOnly = true)
    public List<ChatListEntity> getChatList(String name) {
        log.info("Getting chat list for user: {}", name);
        UserEntity user = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ChatListEntity> chatList = chatListRepository.findByUser(user);

        chatList = chatList.stream()
                .filter(chat -> chat.getChatroom().getUsers().size() > 1 ||
                        !chat.getChatroom().getUsers().iterator().next().equals(user))
                .collect(Collectors.toList());

        log.info("Retrieved {} chats for user: {}", chatList.size(), name);
        return chatList;
    }

    /**
     * 사용자의 최근 채팅 목록을 조회합니다.
     *
     * @param username 사용자 이름
     * @return 최근 채팅 목록
     */
    @Transactional(readOnly = true)
    public List<ChatListEntity> getRecentChats(String username) {
        log.info("Getting recent chats for user: {}", username);
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ChatListEntity> recentChats = chatListRepository.findByUserOrderByIdDesc(user);
        log.info("Retrieved {} recent chats for user: {}", recentChats.size(), username);
        return recentChats;
    }

    /**
     * 채팅방을 생성하거나 기존 채팅방을 조회합니다.
     *
     * @param usernames 채팅방에 포함될 사용자 이름 목록
     * @return 채팅방 ID
     */
    @Transactional
    public Long getOrCreateChatroom(List<String> usernames) {
        log.info("Getting or creating chatroom for users: {}", usernames);
        List<UserEntity> users = userRepository.findAllByNameIn(usernames);
        if (users.size() != usernames.size()) {
            log.error("One or more users not found: {}", usernames);
            throw new RuntimeException("One or more users not found");
        }

        List<ChatroomEntity> existingChatrooms = chatroomRepository.findByUsersContaining(users, users.size());

        if (!existingChatrooms.isEmpty()) {
            log.info("Existing chatroom found. ID: {}", existingChatrooms.get(0).getId());
            return existingChatrooms.get(0).getId();
        }

        List<Long> userIds = users.stream().map(UserEntity::getId).collect(Collectors.toList());
        ChatroomEntity newChatroom = createChatroom(userIds);
        log.info("New chatroom created. ID: {}", newChatroom.getId());
        return newChatroom.getId();
    }

    /**
     * 새로운 채팅방을 생성합니다.
     *
     * @param userIds 채팅방에 포함될 사용자 ID 목록
     * @return 생성된 채팅방 엔티티
     */
    @Transactional
    public ChatroomEntity createChatroom(List<Long> userIds) {
        List<UserEntity> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new RuntimeException("One or more users not found");
        }

        ChatroomEntity chatroom = ChatroomEntity.builder()
                .name(users.stream().map(UserEntity::getName).collect(Collectors.joining(", ")))
                .build();
        users.forEach(chatroom::addUser);
        ChatroomEntity savedChatroom = chatroomRepository.save(chatroom);

        users.forEach(user -> {
            ChatListEntity chatList = ChatListEntity.builder()
                    .user(user)
                    .chatroom(savedChatroom)
                    .build();
            chatListRepository.save(chatList);
        });

        return savedChatroom;
    }

    /**
     * 채팅방 정보를 조회합니다.
     *
     * @param id 채팅방 ID
     * @return 채팅방 엔티티
     */
    @Transactional(readOnly = true)
    public ChatroomEntity getChatroom(Long id) {
        log.info("Getting chatroom with id: {}", id);
        ChatroomEntity chatroom = chatroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
        log.info("Chatroom found. ID: {}, Name: {}", chatroom.getId(), chatroom.getName());
        return chatroom;
    }

    /**
     * 사용자가 채팅방에 접근 권한이 있는지 확인합니다.
     *
     * @param username 사용자 이름
     * @param chatroom 채팅방 엔티티
     * @return 접근 권한 여부
     */
    @Transactional(readOnly = true)
    public boolean hasAccess(String username, ChatroomEntity chatroom) {
        log.info("Checking access for user: {} to chatroom: {}", username, chatroom.getId());
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean hasAccess = chatroom.getUsers().contains(user);
        log.info("Access check result for user: {} to chatroom: {} is: {}", username, chatroom.getId(), hasAccess);
        return hasAccess;
    }

    /**
     * 채팅방의 메시지 목록을 조회합니다.
     *
     * @param chatroomId 채팅방 ID
     * @return 메시지 엔티티 목록
     */
    @Transactional(readOnly = true)
    public List<MessageEntity> getChatroomMessages(Long chatroomId) {
        log.info("Getting messages for chatroom: {}", chatroomId);
        ChatroomEntity chatroom = getChatroom(chatroomId);
        List<MessageEntity> messages = messageRepository.findByChatroomOrderByIdAsc(chatroom);
        log.info("Retrieved {} messages for chatroom: {}", messages.size(), chatroomId);
        return messages;
    }

    /**
     * 새로운 메시지를 저장합니다.
     *
     * @param chatMessage 채팅 메시지 객체
     * @return 저장된 메시지 엔티티
     */
    @Transactional
    public MessageEntity saveMessage(ChatMessage chatMessage) {
        log.info("Saving message for chatroom: {}", chatMessage.getChatroomId());
        ChatroomEntity chatroom = chatroomRepository.findById(chatMessage.getChatroomId())
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
        UserEntity sender = userRepository.findByName(chatMessage.getSender())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MessageEntity messageEntity = MessageEntity.builder()
                .chatroom(chatroom)
                .sender(sender)
                .message(chatMessage.getMessage())
                .build();

        String redisKey = "chat:room:" + chatMessage.getChatroomId();
        try {
            redisTemplate.opsForList().rightPush(redisKey, chatMessage);
            log.info("Message saved to Redis. Key: {}", redisKey);
        } catch (Exception e) {
            log.error("Failed to save message to Redis", e);
        }

        MessageEntity savedMessage = messageRepository.save(messageEntity);
        log.info("Message saved to DB. ID: {}", savedMessage.getId());

        updateChatLists(chatroom);

        return savedMessage;
    }

    private void updateChatLists(ChatroomEntity chatroom) {
        List<ChatListEntity> chatLists = chatListRepository.findByChatroom(chatroom);
        for (ChatListEntity chatList : chatLists) {
            chatList.setLastMessageTime(LocalDateTime.now());
            chatListRepository.save(chatList);
        }
        log.info("Updated {} chat lists for chatroom: {}", chatLists.size(), chatroom.getId());
    }

    /**
     * Redis에서 최근 메시지를 조회합니다.
     *
     * @param chatroomId 채팅방 ID
     * @param count 조회할 메시지 수
     * @return 채팅 메시지 목록
     */
    public List<ChatMessage> getRecentMessagesFromRedis(Long chatroomId, int count) {
        String redisKey = "chat:room:" + chatroomId;
        List<ChatMessage> messages = redisTemplate.opsForList().range(redisKey, -count, -1);
        log.info("Retrieved {} recent messages from Redis for chatroom: {}", messages.size(), chatroomId);
        return messages;
    }

    /**
     * 채팅방에 새로운 사용자를 초대합니다.
     *
     * @param chatroomId 채팅방 ID
     * @param userIds 초대할 사용자 ID 목록
     */
    @Transactional
    public void inviteUsers(Long chatroomId, List<Long> userIds) {
        ChatroomEntity chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
        List<UserEntity> users = userRepository.findAllById(userIds);

        if (users.size() != userIds.size()) {
            throw new RuntimeException("One or more users not found");
        }

        for (UserEntity user : users) {
            if (!chatroom.getUsers().contains(user)) {
                chatroom.addUser(user);
                ChatListEntity chatList = ChatListEntity.builder()
                        .user(user)
                        .chatroom(chatroom)
                        .build();
                chatListRepository.save(chatList);
            }
        }

        chatroomRepository.save(chatroom);
    }

    /**
     * 사용자가 채팅방을 나갑니다.
     *
     * @param chatroomId 채팅방 ID
     * @param userId 사용자 ID
     */
    @Transactional
    public void leaveChat(Long chatroomId, Long userId) {
        log.info("User: {} leaving chatroom: {}", userId, chatroomId);
        ChatroomEntity chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new RuntimeException("Chatroom not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        chatroom.getUsers().remove(user);
        chatroomRepository.save(chatroom);

        ChatListEntity chatList = chatListRepository.findByUserAndChatroom(user, chatroom);
        if (chatList != null) {
            chatListRepository.delete(chatList);
            log.info("ChatList entry deleted for user: {} in chatroom: {}", userId, chatroomId);
        }

        log.info("User: {} successfully left chatroom: {}", userId, chatroomId);
    }

    /**
     * 채팅방의 마지막 메시지 정보를 조회합니다.
     *
     * @param chatroomId 채팅방 ID
     * @return 마지막 메시지 정보
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getLastMessageInfo(Long chatroomId) {
        log.info("Getting last message info for chatroom: {}", chatroomId);
        ChatroomEntity chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Chatroom not found"));

        MessageEntity lastMessage = messageRepository.findTopByChatroomOrderByIdDesc(chatroom);
        Map<String, Object> result = new HashMap<>();
        if (lastMessage != null) {
            result.put("timestamp", lastMessage.getRegistrationDate());
            result.put("message", lastMessage.getMessage());
            result.put("sender", lastMessage.getSender().getName());
            log.info("Last message found for chatroom: {}. Sender: {}", chatroomId, lastMessage.getSender().getName());
        } else {
            log.info("No messages found for chatroom: {}", chatroomId);
        }
        return result;
    }


    @Transactional
    public ChatroomEntity createChatroomWithUserIds(List<Long> userIds) {
        log.info("Creating chatroom for user IDs: {}", userIds);
        List<UserEntity> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            log.error("One or more users not found. User IDs: {}, Found users: {}", userIds, users.size());
            throw new RuntimeException("One or more users not found");
        }

        ChatroomEntity chatroom = ChatroomEntity.builder()
                .name(users.stream().map(UserEntity::getName).collect(Collectors.joining(", ")))
                .build();
        users.forEach(chatroom::addUser);
        chatroom = chatroomRepository.save(chatroom);
        log.info("Chatroom created. ID: {}, Name: {}", chatroom.getId(), chatroom.getName());

        ChatroomEntity finalChatroom = chatroom;
        users.forEach(user -> {
            ChatListEntity chatList = ChatListEntity.builder()
                    .user(user)
                    .chatroom(finalChatroom)
                    .build();
            chatListRepository.save(chatList);
            log.info("ChatList entry created for user: {} in chatroom: {}", user.getId(), finalChatroom.getId());
        });

        return chatroom;
    }

}