package likelion.eight.domain.review.controller;

import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;
    private final UserService userService;


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

    @GetMapping("/review/new")
    public String createReviewForm(Model model, @RequestParam Long userId, @RequestParam Long courseId) {
        //TODO : [user_id랑 course_id 조합이 유일할 때 리뷰 쓰기 가능하도록]

        Course course = courseService.findCourseById(courseId);
        User user = userService.findUserById(userId);

        model.addAttribute("course", course);
        model.addAttribute("user", user);
        model.addAttribute("reviewCreateRequest", ReviewCreateRequest.builder().build());

        return "review/reviewRegisterForm";
    }

    @PostMapping("/review/new")
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
