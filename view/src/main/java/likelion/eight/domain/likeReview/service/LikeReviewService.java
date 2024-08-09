package likelion.eight.domain.likeReview.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.likeReview.model.LikeReviewRes;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.likeReview.LikeReviewEntity;
import likelion.eight.likeReview.LikeReviewJpaRepository;
import likelion.eight.review.ReviewEntity;
import likelion.eight.review.ifs.ReviewJpaRepository;
import likelion.eight.user.UserEntity;
import likelion.eight.user.ifs.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeReviewService {
    private final LikeReviewJpaRepository likeReviewJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public String likeReview(LoginUser loginUser,Long reviewId){
        ReviewEntity review = reviewJpaRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("review", reviewId));
        UserEntity user = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user", loginUser.getId()));

        Optional<LikeReviewEntity> existingLikeReview = likeReviewJpaRepository.findByUserEntityAndReviewEntity(user, review);

        if(existingLikeReview.isPresent()){
            likeReviewJpaRepository.delete(existingLikeReview.get());
            return "리뷰 좋아요 삭제";
        }

        LikeReviewEntity likeReview = LikeReviewEntity.builder()
                .userEntity(user)
                .reviewEntity(review)
                .build();
        likeReviewJpaRepository.save(likeReview);
        return "리뷰 좋아요 등록";
    }

    public List<LikeReviewRes> getAllLikeReviews(LoginUser loginUser){
        UserEntity user = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user", loginUser.getId()));
        List<LikeReviewEntity> allLikeReviews = likeReviewJpaRepository.findAllByUserEntity(user);

        return allLikeReviews.stream().map(likes-> new LikeReviewRes(
                likes.getReviewEntity().getTitle(),
                likes.getReviewEntity().getOneLineReview(),
                likes.getReviewEntity().getRating(),
                likes.getReviewEntity().getUserEntity().getName()
        )).collect(Collectors.toList());
    }
}
