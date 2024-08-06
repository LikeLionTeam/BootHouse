package likelion.eight.domain.review.infra;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.converter.ReviewConverter;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.review.ReviewEntity;
import likelion.eight.review.ifs.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
// 리뷰 리포지토리(인터페이스) 상속
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository; //JPA 리포지토리 사용

    @Override
    public Review save(Review review, CourseEntity courseEntity) {
        ReviewEntity reviewEntity = ReviewConverter.toReviewEntity(review, courseEntity); // TODO :: courseEntity 나중에 넣기
        reviewEntity = reviewJpaRepository.save(reviewEntity);
        return ReviewConverter.toDto(reviewEntity);
    }

    @Override
    public Review getById(Long id) {
        return reviewJpaRepository.findById(id)
                .map(ReviewConverter::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found : " + id));
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewJpaRepository.findById(id)
                .map(ReviewConverter::toDto);
    }

    @Override
    public List<Review> findByCourseId(Long courseId) {
        List<ReviewEntity> reviewEntities = reviewJpaRepository.findByCourseId(courseId);
        return reviewEntities.stream()
                .map(ReviewConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> findAll() {
        List<ReviewEntity> reviewEntities = reviewJpaRepository.findAll();
        return reviewEntities.stream()
                .map(ReviewConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        reviewJpaRepository.deleteById(id);
    }

}
