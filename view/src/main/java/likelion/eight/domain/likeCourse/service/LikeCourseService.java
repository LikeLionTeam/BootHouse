package likelion.eight.domain.likeCourse.service;

import likelion.eight.likeCourse.LikeCourseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCourseService {
    private final LikeCourseJpaRepository likeCourseJpaRepository;
}
