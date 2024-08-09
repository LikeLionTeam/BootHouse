package likelion.eight.message.ifs;

import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.message.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByChatroomOrderByIdAsc(ChatroomEntity chatroom);
}