package likelion.eight.domain.token.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Token {
    private final String token;
    private final LocalDateTime expiredAt;

    @Builder
    public Token(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
