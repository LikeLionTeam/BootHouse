package likelion.eight.domain.course.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.course.service.CourseCrawlingService;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseRestController {
    private final CourseService courseService;
    private final CourseCrawlingService courseCrawlingService;

    @Value("${naver.client.id}")
    private String clientId;

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

    @GetMapping("/api/naver/maps-script")
    public ResponseEntity<String> getMapScript() {
        String url = "https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=" + clientId;
        return ResponseEntity.ok(url);  // 단순히 URL 문자열만 반환
    }

    //크롤링을 위한 컨트롤러
    @GetMapping("/crawling")
    public void crawling() throws InterruptedException {
        courseCrawlingService.crawling();
    }
}
