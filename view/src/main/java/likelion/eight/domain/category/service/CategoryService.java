package likelion.eight.domain.category.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.category.model.Category;
import likelion.eight.domain.category.service.port.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll(){
        List<Category> categories = categoryRepository.findAll();
        return Optional.ofNullable(categories)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No categories found."));
    }

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", id));
    }
}
