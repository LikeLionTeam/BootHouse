package likelion.eight.domain.review.controller;

import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.findAllReviews());
        return "review/showAllReviews";
    }

    @GetMapping("/reviews/{id}")
    public String showReview(@PathVariable Long id, Model model) {

        Review review = reviewService.findReviewById(id);
        model.addAttribute("review", review);

        return "review/showReview";
    }



}
