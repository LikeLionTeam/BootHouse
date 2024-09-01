package likelion.eight.message.ifs;

import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.message.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
//    List<MessageEntity> findByChatroomOrderByIdAsc(ChatroomEntity chatroom);
    MessageEntity findTopByChatroomOrderByIdDesc(ChatroomEntity chatroom);
    List<MessageEntity> findByChatroomAndRegistrationDateBeforeOrderByIdAsc(ChatroomEntity chatroom, LocalDateTime date);
}