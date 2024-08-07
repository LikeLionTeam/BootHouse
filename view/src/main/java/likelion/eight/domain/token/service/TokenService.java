package likelion.eight.domain.token.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.token.helper.ifs.TokenHelperIfs;
import likelion.eight.domain.token.model.Token;
import likelion.eight.domain.token.model.TokenResponse;
import likelion.eight.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static likelion.eight.common.service.CookieService.ADMIN_TOKEN_CODE;
import static likelion.eight.common.service.CookieService.USER_TOKEN_CODE;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String USER_ID = "userId";
    private final TokenHelperIfs tokenHelper;
    public static final String USER_TOKEN_CODE = "userTokenCode";
    public static final String ADMIN_TOKEN_CODE = "adminTokenCode";

    public TokenResponse issueToken(User user){

        return Optional.ofNullable(user)
                .map(User::getId)
                .map(id -> {
                    Token accessToken = createAccessToken(id);
                    Token refreshToken = createRefreshToken(id);
                    return TokenResponse.from(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Token User", user.getId()));
    }

    private Token createAccessToken(Long userId){
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelper.issueAccessToken(data);
    }

    private Token createRefreshToken(Long userId){
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelper.issueRefreshToken(data);
    }

    // JWT 토큰의 유효성 검사 후, 유효한 경우 사용자 ID 반환. 토큰이 유효하지 않거나, 사용자 ID가 없다면 예외를 던짐
    public Long validationToken(String token){
        Map<String, Object> map = tokenHelper.validationTokenWithThrow(token);
        Object userId = map.get(USER_ID);

        Objects.requireNonNull(userId, () -> {
            throw new ResourceNotFoundException("Token UserId Null");
        });

        return Long.parseLong(userId.toString());
    }

    // 로그인 여부 확인 메서드 추가
    public boolean isUserLoggedIn(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (USER_TOKEN_CODE.equals(cookie.getName()) || ADMIN_TOKEN_CODE.equals(cookie.getName()) ){
                    try {
                        validationToken(cookie.getValue()); // 토큰 유효성 검사
                        return true; // 유효한 경우, 로그인 상태
                    } catch (Exception e){
                        // validationToken에서 발생한 예외로, 로그인하지 않은 상태로 간주
                    }
                }
            }
        }
        return false; // 쿠키가 없거나, 유효한 토큰이 없는 경우
    }
}
