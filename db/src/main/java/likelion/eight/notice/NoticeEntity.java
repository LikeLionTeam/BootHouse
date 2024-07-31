package likelion.eight.notice;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.notice.enums.PostType;
import likelion.eight.user.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "notices")
@Getter @Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Column(length = 50, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Lob
    private byte[] image;
}
