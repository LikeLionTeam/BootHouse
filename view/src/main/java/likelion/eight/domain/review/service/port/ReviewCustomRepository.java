package likelion.eight.domain.review.service.port;


import likelion.eight.common.index.controller.model.ReviewDto;

import java.util.List;

public interface ReviewCustomRepository {
    List<ReviewDto> findTop4ByOrderByRatingDesc();
}
