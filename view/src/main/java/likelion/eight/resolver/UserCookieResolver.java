package likelion.eight.resolver;

import likelion.eight.common.annotation.Login;
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
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        Object userId = requestContext.getAttribute(USER_ID, RequestAttributes.SCOPE_REQUEST);
        Object roleType = requestContext.getAttribute(ROLE_TYPE, RequestAttributes.SCOPE_REQUEST);

        log.info("{} , {} 감지함. ",userId, roleType);

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
    }
}
