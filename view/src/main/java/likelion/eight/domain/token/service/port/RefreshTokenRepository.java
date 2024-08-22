package likelion.eight.domain.token.service.port;

import likelion.eight.domain.token.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken saveRefreshToken(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Long userId);
}
