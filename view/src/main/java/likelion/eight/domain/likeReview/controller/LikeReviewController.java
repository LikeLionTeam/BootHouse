package likelion.eight.domain.likeReview.controller;

import likelion.eight.domain.likeReview.service.LikeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LikeReviewController {

    private final LikeReviewService likeReviewService;
}
