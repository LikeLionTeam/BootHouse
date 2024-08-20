package likelion.eight.domain.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.service.CookieService;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.controller.model.ReviewSortCondition;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.UserResponse;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// 로그인 안한 사용자
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;
    private final CookieService cookieService;
    private final UserService userService;


    @GetMapping()
    public String showAllReviews(
            Model model,
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request
    ) {
        return renderReviewPage(model, pageable, request, new ReviewSearchCondition(), new ReviewSortCondition());
    }

    @GetMapping("/search")
    public String searchReviews(
            Model model,
            @RequestParam String keyword,
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request
    ) {
        //검색 기능
        ReviewSearchCondition searchCondition = new ReviewSearchCondition(keyword);

        return renderReviewPage(model, pageable, request, searchCondition, new ReviewSortCondition());
    }

    @GetMapping("/sort")
    public String sortReviews(
            Model model,
            @RequestParam String sortBy,
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request
    ) {
        //정렬 기능 :  높은 평점순 / 낮은 평점순 / 조회수 / 최근순 / 오래된순
        ReviewSortCondition sortCondition = new ReviewSortCondition(sortBy);
        return renderReviewPage(model, pageable, request, new ReviewSearchCondition(), sortCondition);
    }


    @GetMapping("/{reviewId}")
    public String nonUserShowReview(@PathVariable Long reviewId, Model model, HttpServletRequest request) {

        reviewService.incrementViewCount(reviewId);

        Review review = reviewService.findReviewById(reviewId);
        Optional<Review> previousReviewOptional = reviewService.findPreviousReview(reviewId);
        Optional<Review> nextReviewOptional = reviewService.findNextReview(reviewId);
        UserResponse user = userService.getById(review.getUserId());
        boolean isUserLoggedIn = cookieService.isUserLoggedIn(request);

        Course course = courseService.findCourseById(review.getCourseId());

        model.addAttribute("course", course);
        model.addAttribute("review", review);
        model.addAttribute("user", user);
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        previousReviewOptional.ifPresent(previousReview -> model.addAttribute("previousReview", previousReview));
        nextReviewOptional.ifPresent(nextReview -> model.addAttribute("nextReview", nextReview));


        return "review/showReview";
    }

    private String renderReviewPage(Model model,
                                    Pageable pageable,
                                    HttpServletRequest request,
                                    ReviewSearchCondition searchCondition,
                                    ReviewSortCondition sortCondition) {
        Page<Review> reviewPage;
        boolean isUserLoggedIn = (request != null) && cookieService.isUserLoggedIn(request);

        if (searchCondition.getKeyword() != null && !searchCondition.getKeyword().isEmpty()) {
            reviewPage = reviewService.searchReviews(searchCondition, pageable);
        } else if (sortCondition.getSortBy() != null && !sortCondition.getSortBy().isEmpty()) {
            reviewPage = reviewService.sortReviews(sortCondition, pageable);
        } else {
            reviewPage = reviewService.findAllReviews(pageable);
        }

        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("sortCondition", sortCondition);
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);

        return "review/showAllReviews";
    }
}
