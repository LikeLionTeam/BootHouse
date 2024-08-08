package likelion.eight.domain.review.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 로그인 안한 사용자도 볼 수 있음

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;

    @GetMapping("/reviews")
    public String showAllReviews(Model model, ReviewSearchCondition condition) {

        //검색 기능
        List<Review> reviews = reviewService.searchReviews(condition);

        model.addAttribute("reviews", reviews);
        model.addAttribute("condition", condition);

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/{reviewId}")
    public String showReview(@PathVariable Long reviewId, Model model, @Login LoginUser loginUser) {

        Review review = reviewService.findReviewById(reviewId);
        reviewService.incrementViewcount(reviewId);
        Course course = courseService.findCourseById(review.getCourseId());

        model.addAttribute("course", course);
        model.addAttribute("review", review);
        model.addAttribute("loginUser", loginUser);
        return "review/showReview";
    }



}
