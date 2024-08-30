package likelion.eight.domain.user.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.controller.model.UserResponse;
import likelion.eight.domain.user.service.UserService;
import likelion.eight.domain.userCourse.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/myPage")
public class MyPageController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final UserCourseService userCourseService;
    private final CourseService courseService;

    @GetMapping("")
    public String showMyPage(
            @Login LoginUser loginUser,
            Model model) {

        UserResponse user = userService.getById(loginUser.getId());
        List<Review> reviews = reviewService.findByUserId(loginUser.getId());
        List<Long> courseId = userCourseService.findCourseIdsByUserId(user.getId());

        ArrayList<Course> courses = new ArrayList<>();

        for (Long id : courseId) {
            Course course = courseService.findCourseById(id);
            courses.add(course);
        }


        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("courses", courses);


        return "myPage/myPageMain";
    }
}
