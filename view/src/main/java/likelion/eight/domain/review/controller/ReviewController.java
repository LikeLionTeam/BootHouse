package likelion.eight.domain.review.controller;

import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/review/new")
    public String createReviewForm(Model model) {

        model.addAttribute("reviewCreateRequest", ReviewCreateRequest.builder().build());

        return "review/reviewRegisterForm";
    }

    @PostMapping("/review/new")
    public String createReview(@ModelAttribute ReviewCreateRequest reviewCreateRequest) {

        Review review = Review.builder()
                .title(reviewCreateRequest.getTitle())
                .oneLineReview(reviewCreateRequest.getOneLineReview())
                .advantages(reviewCreateRequest.getAdvantages())
                .disadvantages(reviewCreateRequest.getDisadvantages())
                .instructorEvaluation(reviewCreateRequest.getInstructorEvaluation())
                .rating(reviewCreateRequest.getRating())
                .courseId(1L)  // 임시로 course_id를 1로 설정
                .build();

        reviewService.saveReview(review);

        return "redirect:/reviews";
    }

    @GetMapping("review/{id}/edit")
    public String editReviewForm(@PathVariable Long id, Model model) {
        Review review = reviewService.findReviewById(id);
        model.addAttribute("review", review);
        return "review/editReviewForm";
    }

    @PostMapping("review/{id}/edit")
    public String editReview(@PathVariable Long id, @ModelAttribute ReviewUpdateRequest reviewUpdateRequest) {

        reviewService.updateReview(id, reviewUpdateRequest);
        return "redirect:/reviews";
    }

    @PostMapping("review/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

}
