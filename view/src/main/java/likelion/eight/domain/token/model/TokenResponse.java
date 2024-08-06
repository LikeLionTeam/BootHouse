package likelion.eight.domain.token.model;

import likelion.eight.common.domain.exception.CertificationFailedException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class TokenResponse {

    private final String accessToken;
    private final LocalDateTime accessTokenExpiredAt;
    private final String refreshToken;
    private final LocalDateTime refreshTokenExpiredAt;

    @Builder
    public TokenResponse(String accessToken, LocalDateTime accessTokenExpiredAt, String refreshToken, LocalDateTime refreshTokenExpiredAt) {
        this.accessToken = accessToken;
        this.accessTokenExpiredAt = accessTokenExpiredAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public static TokenResponse from(
            Token accessToken,
            Token refreshToke
    ){
        Objects.requireNonNull(accessToken, () -> {throw new CertificationFailedException("유효하지 않은 토큰");});
        Objects.requireNonNull(refreshToke, () -> {throw new CertificationFailedException("유효하지 않은 토큰");});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToke.getToken())
                .refreshTokenExpiredAt(refreshToke.getExpiredAt())
                .build();
    }
}
