package likelion.eight.domain.review.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.eight.common.index.controller.model.ReviewDto;
import likelion.eight.domain.review.converter.ReviewConverter;
import likelion.eight.domain.review.service.port.ReviewCustomRepository;
import likelion.eight.review.ReviewEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    @PersistenceContext
    private EntityManager em;

    // 평점순 + 최대 4개 Course 반환
    @Override
    public List<ReviewDto> findTop4ByOrderByRatingDesc() {
        List<ReviewEntity> reviewEntities = em.createQuery("select r from ReviewEntity r order by r.rating", ReviewEntity.class)
                .setMaxResults(4)
                .getResultList();

        return reviewEntities.stream()
                .map(ReviewConverter::toReviewDto)
                .collect(Collectors.toList());
    }
}
