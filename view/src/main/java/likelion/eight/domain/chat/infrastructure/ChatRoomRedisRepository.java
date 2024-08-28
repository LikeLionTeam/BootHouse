package likelion.eight.domain.chat.infrastructure;

import likelion.eight.domain.chat.model.ChatMessage;
import likelion.eight.domain.chat.model.ChatRoom;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChatRoomRedisRepository {

    private static final String CHAT_ROOMS = "ChatRoom";
    private final HashOperations<String, String, ChatRoom> hashOperations;

    public ChatRoomRedisRepository(RedisTemplate<String, ChatMessage> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(ChatRoom chatRoom) {
        hashOperations.put(CHAT_ROOMS, chatRoom.getId(), chatRoom);
    }

    public Optional<ChatRoom> findById(String id) {
        return Optional.ofNullable(hashOperations.get(CHAT_ROOMS, id));
    }

    public List<ChatRoom> findAll() {
        return hashOperations.values(CHAT_ROOMS);
    }

    public void deleteById(String id) {
        hashOperations.delete(CHAT_ROOMS, id);
    }
}