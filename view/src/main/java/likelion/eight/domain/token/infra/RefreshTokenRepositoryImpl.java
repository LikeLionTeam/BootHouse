package likelion.eight.domain.token.infra;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.token.converter.RefreshTokenConverter;
import likelion.eight.domain.token.model.RefreshToken;
import likelion.eight.domain.token.service.port.RefreshTokenRepository;
import likelion.eight.refreshtoken.RefreshTokenEntity;
import likelion.eight.refreshtoken.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenRepository;
    @Override
    public RefreshToken saveRefreshToken(RefreshToken token) {
        RefreshTokenEntity save = refreshTokenRepository.save(RefreshTokenConverter.toEntity(token));
        return RefreshTokenConverter.toRefreshToken(save);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token).map(RefreshTokenConverter::toRefreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId).map(RefreshTokenConverter::toRefreshToken);
    }
}
