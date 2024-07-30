package likelion.eight.message;

import jakarta.persistence.*;
import likelion.eight.db.chatroom.ChatroomEntity;
import likelion.eight.db.user.UserEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "messages")
@Getter @Setter
public class MessageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatroomEntity chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;
}