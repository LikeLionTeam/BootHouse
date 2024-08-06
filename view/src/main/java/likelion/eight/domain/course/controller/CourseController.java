package likelion.eight.domain.course.controller;

import likelion.eight.domain.category.model.Category;
import likelion.eight.domain.category.service.CategoryService;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.domain.subcourse.model.SubCourse;
import likelion.eight.domain.subcourse.service.SubCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final SubCourseService subCourseService;
    private final ReviewRepository reviewRepository;

    // 모집중인 캠프 조회
    // Todo :: 조회 성능 개선
    @GetMapping("/boothouse/camps")
    public String getOpenCourses(
            Model model,
            @RequestParam(required = false, name = "categories") Long categoryId,
            @ModelAttribute CourseFilter courseFilter,
            @RequestParam(required = false, name = "sort") String sort,
            @RequestParam(required = false, name = "search") String search){
        List<Course> courses;
        List<Category> categories = categoryService.findAll();

        // 모집중인 코스를 기준으로 필터링 (모집중 고려 O, 카테고리 고려 전, 필터링 기준 고려 O)
        courses = courseService.findCoursesByFilters(categoryId, courseFilter, sort, search);

        // 카테고리 조건 추가 (모집중 고려 O, 카테고리 고려 O, 필터링 기준 고려 O)
        if (categoryId != null){
            Category category = categoryService.findById(categoryId);
            List<SubCourse> subCourseList = subCourseService.findSubCourseByCategoryId(categoryId);

            // 선택된 Category와 그에 맞는 SubCourse 모델에 추가
            model.addAttribute("selectedCategory", category);
            model.addAttribute("subCourseList", subCourseList);
        }

        model.addAttribute("courses", courses);
        model.addAttribute("count", courses.size());
        model.addAttribute("categories", categories);

        return "course/courseList";
    }

    // course 자세히보기
    @GetMapping("courses/{id}")
    public String getCourseDetail(@PathVariable(name = "id")Long courseId, Model model){
        Course course = courseService.findCourseById(courseId);
        List<Review> reviews = reviewRepository.findByCourseId(courseId);

        course.calculateAverageRating(reviews);

        model.addAttribute("course", course);
        model.addAttribute("reviews", reviews);
        return "course/courseDetail";
    }
}