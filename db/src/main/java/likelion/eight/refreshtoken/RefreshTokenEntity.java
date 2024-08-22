package likelion.eight.refreshtoken;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(length = 255, nullable = false)
    private String token;

    @Column(nullable = false, name = "expired_at")
    private LocalDateTime expiredAt;
}
