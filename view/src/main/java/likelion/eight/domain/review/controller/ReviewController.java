package likelion.eight.domain.review.controller;

import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.token.service.TokenService;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;
    private final UserService userService;
    private final TokenService tokenService;


    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.findAllReviews());
        return "review/showAllReviews";
    }

    @GetMapping("/reviews/{id}")
    public String showReview(@PathVariable Long id, Model model) {

        //TODO : 로그인 유저 = review의 user_id, 일치하면 수정,삭제 가능하도록 파라미터 넘겨 주기

        Review review = reviewService.findReviewById(id);
        model.addAttribute("review", review);

        return "review/showReview";
    }

    @GetMapping("/review/new/{courseId}")
    public String createReviewForm(Model model, @PathVariable Long courseId,@RequestHeader("Authorization") String token) {
        //TODO : [user_id랑 course_id 조합이 유일할 때 리뷰 쓰기 가능하도록]

        // JWT 로그인 유저 찾아서 넣기

        // JWT 토큰에서 Bearer 접두사를 제거
        String jwtToken = token.substring(7);

        // JWT 토큰을 사용하여 로그인한 사용자 ID를 가져오기
        Long userId = tokenService.validationToken(jwtToken);

        User user = userService.findUserById(userId);

        Course course = courseService.findCourseById(courseId);

        // userId와 courseId 조합이 이미 존재하는지 확인
        boolean reviewExists = reviewService.existsByUserIdAndCourseId(userId, courseId);
        if (reviewExists) {
            return "redirect:/reviews/"; // 이미 리뷰가 존재하는 경우 리다이렉트
        }

        model.addAttribute("course", course);
        model.addAttribute("user", user);
        model.addAttribute("reviewCreateRequest", ReviewCreateRequest.builder().build());

        return "review/reviewRegisterForm";
    }

    @PostMapping("/review/new/{courseId}")
    public String createReview(@ModelAttribute ReviewCreateRequest reviewCreateRequest) {

        //TODO : review에 user_id로 User 찾아서 넣기
        //TODO : review에 course_id로 Course 찾아서 넣기

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

        //TODO : 로그인 유저 = review의 user_id, 일치하면 수정,삭제 가능하도록 파라미터 넘겨 주기

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
        //TODO : 로그인 유저 = review의 user_id, 일치하면 수정,삭제 가능하도록 파라미터 넘겨 주기

        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

}
