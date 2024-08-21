package likelion.eight.domain.bootcamp.controller;

import likelion.eight.domain.bootcamp.controller.model.BootCampSearchCond;
import likelion.eight.domain.bootcamp.controller.model.BootCampSearchResponse;
import likelion.eight.domain.bootcamp.controller.model.BootcampCreateRequest;
import likelion.eight.domain.bootcamp.model.Bootcamp;
import likelion.eight.domain.bootcamp.service.BootcampService;
import likelion.eight.domain.bootcamp.service.port.BootcampRepository;
import likelion.eight.domain.course.model.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bootcamps")
@RequiredArgsConstructor
@Slf4j
public class BootcampController {
    private final BootcampService bootcampService;

    @GetMapping
    public String showAllBootcamps(@RequestParam(name = "search", required = false)String search, Model model){
        List<Bootcamp> bootcamps = bootcampService.findAllBootcamps(search);
        model.addAttribute("bootcamps", bootcamps);

        return "bootcamp/list";
    }

    @GetMapping("/create")
    public String showRegisterForm(
            @ModelAttribute(name = "request") BootcampCreateRequest request){
        return "bootcamp/createForm";
    }

    @GetMapping("/{bootcampId}/courses")
    public String showCoursesByBootcamp(@PathVariable Long bootcampId, Model model){
        List<Course> courses = bootcampService.findCourseByBootcampId(bootcampId);
        model.addAttribute("courses", courses);

        return "bootcamp/courses";
    }

    @PostMapping("/create")
    public String createBootcamp(
            @ModelAttribute(name = "request") BootcampCreateRequest request
    ){
        bootcampService.createBootcamp(request);

        return "redirect:/bootcamps";
    }
}
