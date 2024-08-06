package likelion.eight.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.controller.model.UserCreateRequest;
import likelion.eight.domain.user.controller.model.UserLoginRequest;
import likelion.eight.domain.user.controller.model.UserResponse;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserLoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(
            Model model
    ){
        model.addAttribute("request",  new UserLoginRequest());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginUser(
            @Valid @ModelAttribute("request")
            UserLoginRequest loginRequest,
            BindingResult bindingResult,
            HttpServletResponse response
    ){
        if (bindingResult.hasErrors()) {
            return "login/loginForm"; // 에러가 있을 경우 로그인 폼으로 이동
        }

        try {
            UserResponse loginUser = userService.login(response, loginRequest);

        } catch (ResourceNotFoundException e) {

            bindingResult.reject("loginError", e.getMessage());
            return "login/loginForm"; // 에러 메시지를 설정한 후 로그인 폼으로 이동
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        log.info("Logout request received");
        userService.logout(request, response);
        return "redirect:/";
    }
}
