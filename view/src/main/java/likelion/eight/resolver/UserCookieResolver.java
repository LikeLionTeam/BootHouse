package likelion.eight.resolver;

import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.controller.model.UserResponse;
import likelion.eight.domain.user.service.UserService;
import likelion.eight.user.enums.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static likelion.eight.domain.token.service.TokenService.USER_ID;
import static likelion.eight.interceptor.AuthorizationInterceptor.ROLE_TYPE;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserCookieResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean annotation = parameter.hasParameterAnnotation(Login.class);
        boolean parameterType = parameter.getParameterType().equals(LoginUser.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Login loginAnnotation = parameter.getParameterAnnotation(Login.class);
        boolean isRequired = loginAnnotation != null && loginAnnotation.required(); // required value check

        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        Object userId = requestContext.getAttribute(USER_ID, RequestAttributes.SCOPE_REQUEST);
        Object roleType = requestContext.getAttribute(ROLE_TYPE, RequestAttributes.SCOPE_REQUEST);

        if (userId == null) {
            if (isRequired) {
                throw new CertificationFailedException("비회원 유저. 유저 정보가 필요하나 제공되지 않습니다.");
            }
            return null; // 로그인이 필수가 아닌 경우 null 반환
        }




        log.info("{} , {} 감지함. ",userId, roleType);

//        UserResponse userResponse = userService.getById(Long.parseLong(userId.toString()));
//
//        return LoginUser.builder()
//                .id(userResponse.getId())
//                .name(userResponse.getName())
//                .address(userResponse.getAddress())
//                .email(userResponse.getEmail())
//                .phoneNumber(userResponse.getPhoneNumber())
//                .certificationCode(userResponse.getCertificationCode())
//                .userStatus(userResponse.getUserStatus())
//                .roleType((RoleType) roleType)
//                .image(userResponse.getImage())
//                .build();
        try {
            UserResponse userResponse = userService.getById(Long.parseLong(userId.toString()));
            return LoginUser.builder()
                    .id(userResponse.getId())
                    .name(userResponse.getName())
                    .address(userResponse.getAddress())
                    .email(userResponse.getEmail())
                    .phoneNumber(userResponse.getPhoneNumber())
                    .certificationCode(userResponse.getCertificationCode())
                    .userStatus(userResponse.getUserStatus())
                    .roleType((RoleType) roleType)
                    .image(userResponse.getImage())
                    .build();
        } catch (Exception e) {
            log.error("Error while resolving user: ", e);
            if (isRequired) {
                throw new CertificationFailedException("사용자 정보를 확인할 수 없습니다.");
            }
            return null; // 로그인이 필수가 아닌 경우 null 반환
        }


    }
}
