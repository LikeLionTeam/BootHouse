package likelion.eight.domain.user.controller;

import jakarta.validation.Valid;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.Log;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.user.controller.model.*;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/create")
    public String createForm(
            @ModelAttribute("request") UserCreateRequest request
    ){
        return "user/createForm";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute("request")
            UserCreateRequest request,
            BindingResult bindingResult,
            @RequestParam("verificationCode") String verificationCode
    ) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "user/createForm";
        }

        userService.createUser(request, verificationCode);
        return "redirect:/login";
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            // 인증 코드 이메일 전송
            String certificationCode = userService.sendVerificationCode(email);

            // 인증 코드 클라이언트로 반환
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("code", certificationCode);  // 인증 값 확인하기 위해

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }

    @GetMapping("/verify/{id}")
    public String verifyForm(
            @Login LoginUser loginUser,
            Model model
    ){
        log.info("loginUser={}", loginUser);
        model.addAttribute("id", loginUser.getId());
        return "/user/verifyForm";
    }


    @PostMapping("/verify/{id}")
    public String verifyEmail(
            @PathVariable long id,
            @RequestParam("code") String code
    ){
        log.info("verifyEmail={}", id);
        userService.verifyEmail(id, code);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @Login LoginUser loginUser,
            Model model,
            UserEditRequest request
    ) {

        request.setAddress(loginUser.getAddress());
        request.setPhoneNumber(loginUser.getPhoneNumber());

        model.addAttribute("request", request);
        model.addAttribute("loginUser", loginUser);
        return "/user/editUserForm";
    }


    @PostMapping("/{id}/edit")
    public String editUser(
            @ModelAttribute("request")
            UserEditRequest userEditRequest,
            Model model
            ,@Login LoginUser loginUser
    ) {

        try {
            userService.editUser(loginUser.getId(), userEditRequest);

        } catch (ResourceNotFoundException e) {
            model.addAttribute("EditError", e.getMessage());
            return "/user/editUserForm";
        }
        return "redirect:/myPage";

    }


    private UserCreateRequest init(UserCreateRequest request){
        request.setName("kim");
        request.setAddress("seoul");
        request.setEmail(request.getEmail());
        request.setPhoneNumber(request.getPhoneNumber());
        request.setPassword(request.getPassword());
        return request;
    }
}
