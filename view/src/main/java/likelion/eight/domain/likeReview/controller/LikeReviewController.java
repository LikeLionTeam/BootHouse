package likelion.eight.domain.likeReview.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.likeReview.model.LikeReviewRes;
import likelion.eight.domain.likeReview.service.LikeReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes/review")
public class LikeReviewController {

    private final LikeReviewService likeReviewService;

    @PostMapping("/{reviewId}")
    public ResponseEntity<String> likeReview(@Login LoginUser loginUser, @PathVariable Long reviewId){
        try {
            String result = likeReviewService.likeReview(loginUser, reviewId);
            return ResponseEntity.ok(result);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @GetMapping
    public String getAllLikeReviews(Model model, @Login LoginUser loginUser,
                                    @RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 10);  // 10개씩 페이징 처리
        Page<LikeReviewRes> allLikeReviews = likeReviewService.getAllLikeReviews(loginUser,pageable);
        model.addAttribute("allLikeReviews",allLikeReviews);
        return "likes/likeAllReviews";
    }

}
