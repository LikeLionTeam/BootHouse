package likelion.eight.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.domain.token.service.TokenService;
import likelion.eight.user.enums.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

import static likelion.eight.domain.token.service.TokenCookieService.ADMIN_TOKEN_CODE;
import static likelion.eight.domain.token.service.TokenCookieService.USER_TOKEN_CODE;
import static likelion.eight.domain.token.service.TokenService.USER_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    public static final String ROLE_TYPE = "roleType";
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        Cookie[] cookies = request.getCookies();
        RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        String accessToken = null;

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(USER_TOKEN_CODE.equals(cookie.getName())){
                    accessToken = cookie.getValue();
                    requestContext.setAttribute(ROLE_TYPE, RoleType.USER, RequestAttributes.SCOPE_REQUEST);
                }
                if(ADMIN_TOKEN_CODE.equals(cookie.getName())){
                    accessToken = cookie.getValue();
                    requestContext.setAttribute(ROLE_TYPE, RoleType.ADMIN, RequestAttributes.SCOPE_REQUEST);
                }
            }
//            if(accessToken == null){
//                requestContext.removeAttribute(ROLE_TYPE, RequestAttributes.SCOPE_REQUEST);
//                throw new CertificationFailedException("존재 하지 않은 토큰");
//            }
        }

//        Long userId = tokenService.validationToken(accessToken);
//        if(userId != null){
//            requestContext.setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
//            return true;
//        }
//
//        throw new CertificationFailedException("사용자 인증 실패");

        if (accessToken != null) {
            try {
                Long userId = tokenService.validationToken(accessToken);
                requestContext.setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
                // RoleType 설정 로직
            } catch (CertificationFailedException e) {
                log.warn("Invalid token: {}", e.getMessage());
            }
        }

        // 사용자의 인증 상태를 확인하지 않고 모든 요청을 통과시켜, 비로그인 사용자도 특정 페이지에 접근 가능하게 함
        return true;
    }
}
