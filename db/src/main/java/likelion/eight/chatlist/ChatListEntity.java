package likelion.eight.chatlist;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.BaseTimeEntity;
import likelion.eight.chatroom.ChatroomEntity;
import likelion.eight.user.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "chat_lists")
@Getter @Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatListEntity extends BaseTimeEntity {
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
