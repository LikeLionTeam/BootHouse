package likelion.eight.domain.token.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class RefreshToken {
    private final Long id;
    private final Long userId;
    private final String token;
    private final LocalDateTime expiredAt;

    @Builder
    public RefreshToken(Long id, Long userId, String token, LocalDateTime expiredAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
