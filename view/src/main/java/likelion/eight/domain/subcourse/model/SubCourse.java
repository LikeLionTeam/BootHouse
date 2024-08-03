package likelion.eight.domain.subcourse.model;

import likelion.eight.category.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCourse {
    private final Long id;

    private final String name; // 세부 과정명 (예: 웹풀스택, 백엔드)

    private final CategoryEntity categoryEntity; // 세부과정명(백엔드)는 하나의 카테고리에 속한다(웹에만 속한다, 동시에 모바일도 속할 순 없음)
    @Builder
    public SubCourse(Long id, String name, CategoryEntity categoryEntity) {
        this.id = id;
        this.name = name;
        this.categoryEntity = categoryEntity;
    }
}
