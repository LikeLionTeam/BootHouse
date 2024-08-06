package likelion.eight.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomEntity, Long> {
}
