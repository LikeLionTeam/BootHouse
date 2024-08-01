package likelion.eight.domain.review.infra;

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
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity reviewEntity = ReviewConverter.toReviewEntity(review, null); // TODO :: courseEntity 나중에 넣기
        reviewEntity = reviewJpaRepository.save(reviewEntity);
        return ReviewConverter.toReview(reviewEntity);
    }

    @Override
    public Review getById(Long id) {
        return reviewJpaRepository.findById(id)
                .map(ReviewConverter::toReview)
                .orElseThrow(() -> new IllegalArgumentException("Review Not Found : " + id));
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewJpaRepository.findById(id)
                .map(ReviewConverter::toReview);
    }

    @Override
    public List<Review> findByCourseId(Long courseId) {
        return null;
    }

    @Override
    public List<Review> findAll() {
        List<ReviewEntity> reviewEntities = reviewJpaRepository.findAll();
        return reviewEntities.stream()
                .map(ReviewConverter::toReview)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        reviewJpaRepository.deleteById(id);
    }
}
