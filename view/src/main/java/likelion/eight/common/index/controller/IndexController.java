package likelion.eight.common.index.controller;

import likelion.eight.common.index.controller.model.CourseDto;
import likelion.eight.common.index.controller.model.ReviewDto;
import likelion.eight.common.index.service.IndexService;
import likelion.eight.domain.course.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final IndexService indexService;

    @GetMapping("/")
    public String showIndexPage(Model model){
        List<Course> courses = indexService.findTop4ByOrderByStartDateDesc();
        List<ReviewDto> reviewDtos = indexService.findTop4ByOrderByRatingDesc(); // dto로 바로 받음

        List<CourseDto> courseDtos = courses.stream()
                .map(course -> new CourseDto(course))
                .collect(Collectors.toList());

        model.addAttribute("courseDtos", courseDtos);
        model.addAttribute("reviewDtos", reviewDtos);

        return "index";
    }
}
