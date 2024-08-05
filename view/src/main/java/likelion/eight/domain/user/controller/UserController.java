package likelion.eight.domain.user.controller;

import jakarta.validation.Valid;
import likelion.eight.domain.user.controller.model.UserCreateRequest;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.UserService;
import likelion.eight.user.UserEntity;
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
@RequestMapping("/users")
public class UserController {

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
        return "redirect:/";
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
        request.setAddress("seoul");
        request.setEmail(request.getEmail());
        request.setPhoneNumber(request.getPhoneNumber());
        request.setPassword(request.getPassword());
        return request;
    }
}
