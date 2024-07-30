package likelion.eight.chatlist;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.db.chatroom.ChatroomEntity;
import likelion.eight.db.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat_lists")
@Getter @Setter
public class ChatListEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_list_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatroomEntity chatroom;
}
