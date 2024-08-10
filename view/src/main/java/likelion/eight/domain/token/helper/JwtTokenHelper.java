package likelion.eight.domain.token.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.domain.token.helper.ifs.TokenHelperIfs;
import likelion.eight.domain.token.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper implements TokenHelperIfs {

    private final ClockHolder clockHolder;

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public Token issueAccessToken(Map<String, Object> data) {
        LocalDateTime expiredTime = clockHolder.plusHours(accessTokenPlusHour);
        Date expiredAt = clockHolder.convertAbsoluteTime(expiredTime);

        Algorithm algo = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
        String jwtToken = JWT.create()
                .withPayload(data)
                .withExpiresAt(expiredAt)
                .sign(algo);

        return Token.builder()
                .token(jwtToken)
                .expiredAt(expiredTime)
                .build();
    }

    @Override
    public Token issueRefreshToken(Map<String, Object> data) {
        LocalDateTime refreshTime = clockHolder.plusHours(refreshTokenPlusHour);
        Date refreshAt = clockHolder.convertAbsoluteTime(refreshTime);

        Algorithm algo = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwtToken = JWT.create()
                .withPayload(data)
                .withExpiresAt(refreshAt)
                .sign(algo);

        return Token.builder()
                .token(jwtToken)
                .expiredAt(refreshTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        Algorithm algo = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));

        JWTVerifier verifier = JWT.require(algo).build();

        try{
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, Object> claims = new HashMap<>();
            decodedJWT.getClaims().forEach((k, v) ->
                    claims.put(k, v.as(Object.class)));
            return claims;
        }catch(Exception e){
            if(e instanceof JWTVerificationException){
                throw new CertificationFailedException("유효하지 않은 토큰 입니다.");
            }else if(e instanceof TokenExpiredException){
                throw new CertificationFailedException("만료된 토큰 입니다.");
            }else{
                throw new CertificationFailedException("잘못된 접근 방식입니다.");
            }
        }
    }
}
