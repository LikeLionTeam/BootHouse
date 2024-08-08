package likelion.eight.domain.likeCourse.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.likeCourse.model.LikeCourseRes;
import likelion.eight.domain.likeCourse.service.LikeCourseService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/likes/course")
public class LikeCourseController {
    private final LikeCourseService likeCourseService;

    @GetMapping
    public String getAllCourseLikes(Model model, @Login LoginUser loginUser){
        List<LikeCourseRes> allCourseLikes = likeCourseService.getAllCourseLikes(loginUser);
        model.addAttribute("courses",allCourseLikes);
        return "/likes/likeAllCourse";
    }
}

