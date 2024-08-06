package likelion.eight.chatlist.ifs;

import likelion.eight.chatlist.ChatListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatListJpaRepository extends JpaRepository<ChatListEntity, Long> {
}
