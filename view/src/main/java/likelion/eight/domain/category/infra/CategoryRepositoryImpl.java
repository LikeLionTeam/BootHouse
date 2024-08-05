package likelion.eight.domain.category.infra;

import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import likelion.eight.domain.category.converter.CategoryConverter;
import likelion.eight.domain.category.model.Category;
import likelion.eight.domain.category.service.port.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;
    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoryEntityList = categoryJpaRepository.findAll();
        return CategoryConverter.toCategoryList(categoryEntityList);
    }

    @Override
    public Optional<Category> findById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryJpaRepository.findById(id);
        return CategoryConverter.toCategoryOptional(categoryEntity);
    }
}
