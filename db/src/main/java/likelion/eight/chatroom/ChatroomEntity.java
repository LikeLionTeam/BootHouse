package likelion.eight.chatroom;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.db.BaseEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "chatrooms")
@Getter @Setter
public class ChatroomEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // created, updated.
}
