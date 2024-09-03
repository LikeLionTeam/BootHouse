package likelion.eight.chatroom.ifs;

import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatroomJpaRepository extends JpaRepository<ChatroomEntity, Long> {
    Optional<ChatroomEntity> findByUsersContainingAndUsersContaining(UserEntity user1, UserEntity user2);

    @Query("SELECT c FROM ChatroomEntity c JOIN c.users u WHERE u IN :users GROUP BY c HAVING COUNT(DISTINCT u) = :userCount")
    List<ChatroomEntity> findByUsersContaining(@Param("users") List<UserEntity> users, @Param("userCount") long userCount);
}