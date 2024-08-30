package likelion.eight.domain.subcourse.converter;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.category.converter.CategoryConverter;
import likelion.eight.domain.subcourse.model.SubCourse;

import java.util.List;
import java.util.stream.Collectors;

public class SubCourseConverter {
    public static SubCourseEntity toEntity(SubCourse subCourse){
        return SubCourseEntity.builder()
                .id(subCourse.getId())
                .name(subCourse.getName())
                .categoryEntity(CategoryConverter.toEntity(subCourse.getCategory()))
                .build();
    }

    public static SubCourse toSubcourse(SubCourseEntity subCourseEntity){
        if (subCourseEntity == null){
            throw new ResourceNotFoundException("SubCourseEntity가 null입니다.");
        }

        return SubCourse.builder()
                .id(subCourseEntity.getId())
                .name(subCourseEntity.getName())
                .category(CategoryConverter.toCategory(subCourseEntity.getCategoryEntity()))
                .build();
    }

    public static List<SubCourse> toSubcourseList(List<SubCourseEntity> subCourseEntities){
        return subCourseEntities.stream()
                .map(SubCourseConverter::toSubcourse)
                .collect(Collectors.toList());
    }
}
