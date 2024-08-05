package likelion.eight.domain.category.converter;

import likelion.eight.category.CategoryEntity;
import likelion.eight.domain.category.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryConverter {
    public static CategoryEntity toEntity(Category category){
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(CategoryEntity categoryEntity){
        return Category.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }

    public static Optional<Category> toCategoryOptional(Optional<CategoryEntity> categoryEntity){

        return categoryEntity.map(CategoryConverter::toCategory);
    }

    public static List<Category> toCategoryList(List<CategoryEntity> categoryEntities){
        return categoryEntities.stream()
                .map(CategoryConverter::toCategory)
                .collect(Collectors.toList());
    }
}
