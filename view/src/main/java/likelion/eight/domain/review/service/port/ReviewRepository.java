package likelion.eight.domain.review.service.port;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review, CourseEntity courseEntity);


    Review getById(Long id);

    Optional<Review> findById(Long id);

    List<Review> findByCourseId(Long courseId);

    List<Review> findAll();

    void deleteById(Long id);



}
