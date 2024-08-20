package likelion.eight.domain.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.service.CookieService;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.controller.model.UserResponse;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

// 로그인 사용자만 볼 수 있는 controller

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewWithLoginController {

    private final ReviewService reviewService;
    private final CookieService cookieService;

    @GetMapping("/user/reviews/{reviewId}")
    public String UserShowReview(@PathVariable Long reviewId, Model model, @Login LoginUser loginUser, HttpServletRequest request) {

        reviewService.incrementViewCount(reviewId);

        Review review = reviewService.findReviewById(reviewId);
        Optional<Review> previousReviewOptional = reviewService.findPreviousReview(reviewId);
        Optional<Review> nextReviewOptional = reviewService.findNextReview(reviewId);
        String author = reviewService.getAuthor(review.getUserId());
        String courseName = reviewService.getCourseName(review.getCourseId());
        boolean isUserLoggedIn = cookieService.isUserLoggedIn(request);


        model.addAttribute("courseName", courseName);
        model.addAttribute("review", review);
        model.addAttribute("author", author);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        previousReviewOptional.ifPresent(previousReview -> model.addAttribute("previousReview", previousReview));
        nextReviewOptional.ifPresent(nextReview -> model.addAttribute("nextReview", nextReview));

        return "review/showReview";
    }

    @GetMapping("/reviews/new/{courseId}")
    public String createReviewForm(Model model, @PathVariable Long courseId, @Login LoginUser loginUser, RedirectAttributes redirectAttributes) {

        Long userId = loginUser.getId();

        if (reviewService.existsByUserIdAndCourseId(userId, courseId)) {

            model.addAttribute("error", "이미 해당 코스에 대한 리뷰를 작성하였습니다.");

            Long reviewId = reviewService.findReviewByCourseIdAndUserId(courseId, userId).getId();
            redirectAttributes.addAttribute("reviewId", reviewId);

            return "redirect:/reviews/{reviewId}";
        }


        model.addAttribute("courseId", courseId);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("reviewCreateRequest", ReviewCreateRequest.builder().build());

        return "review/reviewRegisterForm";
    }

    @PostMapping("/reviews/new/{courseId}")
    public String createReview(@ModelAttribute ReviewCreateRequest reviewCreateRequest, @PathVariable Long courseId, @Login LoginUser loginUser) {

        Long userId = loginUser.getId();

        reviewService.saveReview(reviewCreateRequest, userId, courseId);

        return "redirect:/reviews";
    }


    @GetMapping("review/{reviewId}/edit")
    public String editReviewForm(@PathVariable Long reviewId, Model model) {

        Review review = reviewService.findReviewById(reviewId);
        model.addAttribute("review", review);
        return "review/editReviewForm";
    }

    @PostMapping("review/{reviewId}/edit")
    public String editReview(@PathVariable Long reviewId, @ModelAttribute ReviewUpdateRequest reviewUpdateRequest, @Login LoginUser loginUser) {

        reviewService.updateReview(reviewId, reviewUpdateRequest);
        return "redirect:/reviews";
    }

    @PostMapping("review/{reviewId}/delete")
    public String deleteReview(@PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);
        return "redirect:/reviews";
    }
}
