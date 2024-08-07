package likelion.eight.domain.likeReview.service;

import likelion.eight.likeReview.LikeReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeReviewService {
    private final LikeReviewJpaRepository likeReviewJpaRepository;
}
