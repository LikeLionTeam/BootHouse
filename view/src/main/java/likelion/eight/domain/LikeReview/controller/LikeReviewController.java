package likelion.eight.domain.LikeReview.controller;

import likelion.eight.domain.LikeReview.service.LikeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LikeReviewController {

    private final LikeReviewService likeReviewService;
}
