package likelion.eight.chatroom;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "chatrooms")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class ChatroomEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // created, updated.
}
