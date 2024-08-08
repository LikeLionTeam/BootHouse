package likelion.eight.domain.review.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.controller.model.ReviewSortCondition;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// 로그인 안한 사용자도 볼 수 있음

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;

    @GetMapping("/reviews")
    public String showAllReviews(Model model) {

        List<Review> reviews = reviewService.findAllReviews();

        model.addAttribute("reviews", reviews);
        model.addAttribute("searchCondition", new ReviewSearchCondition()); // 검색 조건 초기화
        model.addAttribute("sortCondition", new ReviewSortCondition()); // 정렬 조건 초기화

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/search")
    public String searchReviews(Model model, @RequestParam String keyword) {

        //검색 기능
        ReviewSearchCondition searchCondition = new ReviewSearchCondition(keyword);
        List<Review> reviews = reviewService.searchReviews(searchCondition);

        model.addAttribute("reviews", reviews);
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("sortCondition", new ReviewSortCondition()); // 정렬 조건 초기화

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/sort")
    public String sortReviews(Model model, @RequestParam String sortBy) {
        //정렬 기능 :  높은 평점순 / 낮은 평점순 / 조회수 / 최근순 / 오래된순
        ReviewSortCondition sortCondition = new ReviewSortCondition(sortBy);
        List<Review> reviews = reviewService.sortReviews(sortCondition);

        model.addAttribute("reviews", reviews);
        model.addAttribute("sortCondition", sortCondition);
        model.addAttribute("searchCondition", new ReviewSearchCondition());

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/{reviewId}")
    public String showReview(@PathVariable Long reviewId, Model model, @Login LoginUser loginUser) {

        reviewService.incrementViewcount(reviewId);

        Review review = reviewService.findReviewById(reviewId);
        Optional<Review> previousReviewOptional = reviewService.findPreviousReview(reviewId);
        Optional<Review> nextReviewOptional = reviewService.findNextReview(reviewId);

        Course course = courseService.findCourseById(review.getCourseId());

        model.addAttribute("course", course);
        model.addAttribute("review", review);
        previousReviewOptional.ifPresent(previousReview -> model.addAttribute("previousReview", previousReview));
        nextReviewOptional.ifPresent(nextReview -> model.addAttribute("nextReview", nextReview));
        model.addAttribute("loginUser", loginUser);
        return "review/showReview";
    }



}
