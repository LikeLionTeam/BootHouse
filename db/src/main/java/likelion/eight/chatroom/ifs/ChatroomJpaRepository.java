package likelion.eight.chatroom.ifs;

import likelion.eight.chatroom.ChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomEntity, Long> {
}
