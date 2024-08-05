package likelion.eight.domain.category.service.port;

import likelion.eight.domain.category.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(Long id);
}
