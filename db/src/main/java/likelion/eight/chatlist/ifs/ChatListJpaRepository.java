package likelion.eight.chatlist.ifs;

import likelion.eight.chatlist.ChatListEntity;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatListJpaRepository extends JpaRepository<ChatListEntity, Long> {
    List<ChatListEntity> findByUser(UserEntity user);
    boolean existsByUserAndChatroom(UserEntity user, ChatroomEntity chatroom);
    List<ChatListEntity> findByUserOrderByIdDesc(UserEntity user);

    // 새로 추가된 메서드
    List<ChatListEntity> findByChatroom(ChatroomEntity chatroom);
    // 새로 추가된 메서드
    ChatListEntity findByUserAndChatroom(UserEntity user, ChatroomEntity chatroom);
}