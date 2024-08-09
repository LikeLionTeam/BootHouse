package likelion.eight.message;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.user.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "messages")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)

public class MessageEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatroomEntity chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @Lob
    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;
}