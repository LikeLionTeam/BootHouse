package likelion.eight.notice;

import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {
}
