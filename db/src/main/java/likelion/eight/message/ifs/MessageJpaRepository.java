package likelion.eight.message.ifs;

import likelion.eight.message.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {
}
