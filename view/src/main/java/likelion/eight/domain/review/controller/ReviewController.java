package likelion.eight.domain.review.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 로그인 안한 사용자도 볼 수 있음

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.findAllReviews());
        return "review/showAllReviews";
    }

    @GetMapping("/reviews/{reviewId}")
    public String showReview(@PathVariable Long reviewId, Model model, @Login LoginUser loginUser) {

        Review review = reviewService.findReviewById(reviewId);

        model.addAttribute("review", review);
        model.addAttribute("loginUser", loginUser);
        return "review/showReview";
    }


}
