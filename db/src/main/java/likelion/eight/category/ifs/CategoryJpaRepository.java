package likelion.eight.category.ifs;

import likelion.eight.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
}

