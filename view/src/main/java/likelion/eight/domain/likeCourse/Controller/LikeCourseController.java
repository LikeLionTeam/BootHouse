package likelion.eight.domain.likeCourse.Controller;

import likelion.eight.domain.likeCourse.Service.LikeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class LikeCourseController {
    private final LikeCourseService likeCourseService;
}

