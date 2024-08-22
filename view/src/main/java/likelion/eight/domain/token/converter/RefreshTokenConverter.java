package likelion.eight.domain.token.converter;

import likelion.eight.domain.token.model.RefreshToken;
import likelion.eight.domain.token.model.Token;
import likelion.eight.refreshtoken.RefreshTokenEntity;

public class RefreshTokenConverter {

    public static RefreshToken toRefreshToken(RefreshTokenEntity refreshTokenEntity){
        return RefreshToken.builder()
                .id(refreshTokenEntity.getId())
                .userId(refreshTokenEntity.getUserId())
                .token(refreshTokenEntity.getToken())
                .expiredAt(refreshTokenEntity.getExpiredAt())
                .build();
    }

    public static RefreshToken toRefreshToken(Long userId, Token token){
        return RefreshToken.builder()
                .userId(userId)
                .token(token.getToken())
                .expiredAt(token.getExpiredAt())
                .build();
    }


    public static RefreshTokenEntity toEntity(RefreshToken refreshToken){
        return RefreshTokenEntity.builder()
                .userId(refreshToken.getUserId())
                .token(refreshToken.getToken())
                .expiredAt(refreshToken.getExpiredAt())
                .build();
    }

    public static Token toToken(RefreshToken refreshToken){
        return Token.builder()
                .token(refreshToken.getToken())
                .expiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
