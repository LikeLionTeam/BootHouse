package likelion.eight.chatroom.ifs;

import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomEntity, Long> {
    Optional<ChatroomEntity> findByUsersContainingAndUsersContaining(UserEntity user1, UserEntity user2);
}