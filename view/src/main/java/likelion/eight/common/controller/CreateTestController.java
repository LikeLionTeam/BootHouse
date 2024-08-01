package likelion.eight.common.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import likelion.eight.common.annotation.Password;
import likelion.eight.common.annotation.PhoneNumber;
import likelion.eight.domain.user.controller.model.UserCreateRequest;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.UserService;
import likelion.eight.user.enums.GenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CreateTestController {

    private final UserService userService;


    @GetMapping("/create")
    public String createForm(
            @ModelAttribute("request") UserCreateRequest request
    ){
        log.info("createForm");
        return "user/createForm";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute("request")
            UserCreateRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){

/*        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "user/createForm";
        }*/
        UserCreateRequest init = init(request);

        User user = userService.createUser(init);
        log.info("user={}",user.getPhoneNumber());
        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/{id}/verify";
    }

    @GetMapping("/{id}/verify")
    public String verifyForm(
            @PathVariable long id,
            Model model
    ){
        log.info("verifyForm={}", id);
        model.addAttribute("id", id);
        return "/user/verifyForm";
    }


    @PostMapping("/{id}/verify")
    public String verifyEmail(
            @PathVariable long id,
            @RequestParam("code") String code
    ){
        log.info("verifyEmail={}", id);
        userService.verifyEmail(id, code);
        return "redirect:/";
    }

    private UserCreateRequest init(UserCreateRequest request){
        request.setName("kim");
        request.setNickName("jt");
        request.setAddress("seoul");
        request.setGenderType(GenderType.MALE);
        request.setEmail(request.getEmail());
        request.setPhoneNumber(request.getPhoneNumber());
        request.setPassword(request.getPassword());
        return request;
    }
}
