package likelion.eight.domain.subcourse.infra;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.SubCourseEntity.ifs.SubCourseJpaRepository;
import likelion.eight.domain.subcourse.converter.SubCourseConverter;
import likelion.eight.domain.subcourse.model.SubCourse;
import likelion.eight.domain.subcourse.service.port.SubCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubCourseRepositoryImpl implements SubCourseRepository {
    private final SubCourseJpaRepository subCourseJpaRepository;
    @Override
    public List<SubCourse> findSubCourseByCategoryId(Long categoryId) {
        List<SubCourseEntity> subCourseEntities = subCourseJpaRepository.findSubCourseByCategoryId(categoryId);
        return SubCourseConverter.toSubcourseList(subCourseEntities);
    }
}
