package likelion.eight.domain.category.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Category {

    private Long id;

    private String name;

    @Builder
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
