package likelion.eight.domain.course.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.Map;

import static likelion.eight.domain.token.service.TokenService.USER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseRestController {
    private final CourseService courseService;

    @PostMapping("/courses/{id}/like")
    public ResponseEntity<String> toggleLikeCourse(@PathVariable(name = "id") Long courseId, @Login LoginUser loginUser){
        String message = courseService.toggleLikeCourse(courseId, loginUser.getId());

        return ResponseEntity.ok(message);
    }

    @GetMapping("/courses/{id}/isLiked")
    public ResponseEntity<Map<String, String>> isCourseLiked(@PathVariable(name = "id")Long courseId, @Login LoginUser loginUser){
        boolean isLiked = courseService.isCourseLikedByUser(loginUser.getId(), courseId);
        Map<String, String> response = new HashMap<>();
        response.put("isLiked", String.valueOf(isLiked));

        return ResponseEntity.ok(response);
    }
}
