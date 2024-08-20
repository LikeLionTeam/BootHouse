package likelion.eight.common.index.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.common.index.controller.model.ReviewDto;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseCustomRepository;
import likelion.eight.domain.review.service.port.ReviewCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IndexService {
    private final CourseCustomRepository courseCustomRepository;
    private final ReviewCustomRepository reviewCustomRepository;

    public List<Course> findTop4ByOrderByStartDateDesc(){
        List<Course> courses = courseCustomRepository.findTop4ByOrderByStartDateDesc();

        return Optional.ofNullable(courses)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("개강날짜순으로 정렬된 Course 글 4개가 없습니다."));
    }

    public List<ReviewDto> findTop4ByOrderByRatingDesc(){
        List<ReviewDto> reviews = reviewCustomRepository.findTop4ByOrderByRatingDesc();

        return Optional.ofNullable(reviews)
                .filter(r -> !r.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("평점순으로 정렬된 review 글 4개가 없습니다."));
    }

}
