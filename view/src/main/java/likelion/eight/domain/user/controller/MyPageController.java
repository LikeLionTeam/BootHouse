package likelion.eight.domain.user.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myPage")
public class MyPageController {
    private final ReviewService reviewService;

    @GetMapping("")
    public String showMyPage(
            @Login LoginUser loginUser,
            Model model) {

        List<Review> reviews = reviewService.findByUserId(loginUser.getId());


        model.addAttribute("loginUser", loginUser);
        model.addAttribute("reviews", reviews);

        return "myPage/myPageMain";
    }
}
