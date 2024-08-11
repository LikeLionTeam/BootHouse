package likelion.eight.domain.user.controller;

import jakarta.validation.Valid;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.user.controller.model.*;
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
            BindingResult bindingResult
    ){

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "user/createForm";
        }
        //UserCreateRequest init = init(request);

        userService.createUser(request);
        return "redirect:/";
    }

    @GetMapping("/{id}/verify")
    public String verifyForm(
            @Login LoginUser loginUser,
            Model model
    ){
        log.info("loginUser={}", loginUser);
        model.addAttribute("id", loginUser.getId());
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

    @GetMapping("/{id}/edit")
    public String editForm(
            @Login LoginUser loginUser,
            Model model
    ){
        model.addAttribute("loginUser", loginUser);
        return "/user/editForm";
    }


    @PostMapping("{id}/edit")
    public String editUser(
            @PathVariable Long id,
            @ModelAttribute("request")
            UserEditRequest userEditRequest,
            BindingResult bindingResult
    ){

        try {
            userService.editUser(id, userEditRequest);

        } catch (ResourceNotFoundException e) {

            bindingResult.reject("EditError", e.getMessage());
            return "/user/editForm"; //
        }
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
