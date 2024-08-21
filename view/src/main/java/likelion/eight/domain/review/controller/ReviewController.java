package likelion.eight.domain.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.service.CookieService;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.controller.model.ReviewSortCondition;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @GetMapping()
    public String showAllReviews(
            Model model,
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Login(required = false) LoginUser loginUser
    ) {
        return renderReviewPage(model, pageable, new ReviewSearchCondition(), new ReviewSortCondition(),loginUser);
    }

    @GetMapping("/search")
    public String searchReviews(
            Model model,
            @RequestParam String keyword,
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Login(required = false) LoginUser loginUser
    ) {
        //검색 기능
        ReviewSearchCondition searchCondition = new ReviewSearchCondition(keyword);

        return renderReviewPage(model, pageable,searchCondition, new ReviewSortCondition(),loginUser);
    }

    @GetMapping("/sort")
    public String sortReviews(
            Model model,
            @RequestParam String sortBy,
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @Login(required = false) LoginUser loginUser
    ) {
        //정렬 기능 :  높은 평점순 / 낮은 평점순 / 조회수 / 최근순 / 오래된순
        ReviewSortCondition sortCondition = new ReviewSortCondition(sortBy);
        return renderReviewPage(model, pageable, new ReviewSearchCondition(), sortCondition, loginUser);
    }


    @GetMapping("/{reviewId}")
    public String nonUserShowReview(@PathVariable Long reviewId, Model model, @Login(required = false) LoginUser loginUser) {

        reviewService.incrementViewCount(reviewId); //조회수 증가

        Review review = reviewService.findReviewById(reviewId);

        Optional<Review> previousReviewOptional = reviewService.findPreviousReview(reviewId); // 이전 글
        Optional<Review> nextReviewOptional = reviewService.findNextReview(reviewId); // 다음 글

        String author = reviewService.getAuthor(review.getUserId());
        String courseName = reviewService.getCourseName(review.getCourseId());

        model.addAttribute("courseName", courseName);
        model.addAttribute("review", review);
        model.addAttribute("author", author);
        model.addAttribute("loginUser", loginUser);

        previousReviewOptional.ifPresent(previousReview -> model.addAttribute("previousReview", previousReview));
        nextReviewOptional.ifPresent(nextReview -> model.addAttribute("nextReview", nextReview));


        return "review/showReview";
    }

    private String renderReviewPage(Model model,
                                    Pageable pageable,
                                    ReviewSearchCondition searchCondition,
                                    ReviewSortCondition sortCondition, @Login LoginUser loginUser) {
        Page<Review> reviewPage;

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
        model.addAttribute("loginUser", loginUser);

        return "review/showAllReviews";
    }
}
