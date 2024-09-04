package likelion.eight.domain.likecourse.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.likecourse.model.LikeCourseRes;
import likelion.eight.domain.likecourse.service.LikeCourseService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/likes/course")
public class LikeCourseController {
    private final LikeCourseService likeCourseService;

    @GetMapping
    public String getAllCourseLikes(Model model, @Login LoginUser loginUser,@RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<LikeCourseRes> allCourseLikes = likeCourseService.getAllCourseLikes(loginUser,pageable);
        model.addAttribute("courses",allCourseLikes);
        return "/likes/likeAllCourse";
    }
}

