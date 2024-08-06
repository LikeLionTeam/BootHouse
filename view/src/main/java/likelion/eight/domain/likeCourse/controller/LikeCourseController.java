package likelion.eight.domain.likeCourse.controller;

import likelion.eight.domain.likeCourse.service.LikeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class LikeCourseController {
    private final LikeCourseService likeCourseService;
}

