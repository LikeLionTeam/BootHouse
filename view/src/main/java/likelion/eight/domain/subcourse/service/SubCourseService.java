package likelion.eight.domain.subcourse.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.subcourse.model.SubCourse;
import likelion.eight.domain.subcourse.service.port.SubCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCourseService {
    private final SubCourseRepository subCourseRepository;

    public List<SubCourse> findSubCourseByCategoryId(Long categoryId){
        List<SubCourse> subCourseList = subCourseRepository.findSubCourseByCategoryId(categoryId);
        return Optional.ofNullable(subCourseList)
                .filter(sc -> !sc.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("no subcourse with categoryId : " + categoryId));
    }
}
