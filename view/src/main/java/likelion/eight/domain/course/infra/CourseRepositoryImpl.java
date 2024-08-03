package likelion.eight.domain.course.infra;

import jakarta.persistence.criteria.Predicate;
import likelion.eight.course.CourseEntity;
import likelion.eight.course.ParticipationTime;
import likelion.eight.course.ifs.CourseJpaRepository;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    private final CourseJpaRepository courseJpaRepository;

    // 모집중인 코스만 반환
    @Override
    public List<Course> findByClosingDateAfter(LocalDateTime now) {
        List<CourseEntity> courseEntityList = courseJpaRepository.findByClosingDateAfter(now);
        return CourseConverter.toCourseList(courseEntityList);
    }

    // 카테고리별, 모집중인 코스 반환

    @Override
    public List<Course> findByOpenCoursesByCategory(Long categoryId) {
        List<CourseEntity> courseEntityList = courseJpaRepository.findByOpenCoursesByCategory(categoryId);
        return CourseConverter.toCourseList(courseEntityList);
    }

    // TODO:: 우선은 CriteriaAPI를 사용했는데, 후에 QueryDSL로 동적쿼리 적용해볼 것
    // 필터조건에 맞는 코스 리스트 반환
    @Override
    public List<Course> findCoursesByFilters(Long categoryId, CourseFilter courseFilter, String sort) {
        String onlineOffline = courseFilter.getOnlineOffline();
        String location = mapLocation(courseFilter.getLocation());
        String cost = courseFilter.getCost();
        String participationTime = courseFilter.getParticipationTime();
        String selectionProcedure = courseFilter.getSelectionProcedure();
        Long subCourseId = courseFilter.getSubCourse();

        List<Predicate> predicates = new ArrayList<>();

        // Specification 인터페이스로, JPA의 Criteria API로 동적쿼리 생성
        Specification<CourseEntity> specification = ((root, query, criteriaBuilder) -> {
            // 모집중 필터 -- 항상 '모집중'인 상태 보장
            predicates.add(criteriaBuilder.greaterThan(root.get("closingDate"), LocalDateTime.now()));

            // 카테고리 필터
            if (categoryId != null){
                predicates.add(criteriaBuilder.equal(root.get("categoryEntity").get("id"), categoryId));
            }

            // 온라인/오프라인 필터
            if (onlineOffline != null) {
                boolean isOnline = "online".equalsIgnoreCase(onlineOffline);
                predicates.add(criteriaBuilder.equal(root.get("onlineOffline"), isOnline));
            }

            // 지역 필터
            if (location != null) {
                predicates.add(criteriaBuilder.equal(root.get("location"), location));
            }

            // 비용 필터
            if (cost != null) {
                if ("free".equalsIgnoreCase(cost)) {
                    predicates.add(criteriaBuilder.equal(root.get("tuitionType"), "무료")); // 무료인 경우
                } else if ("paid".equalsIgnoreCase(cost)) {
                    predicates.add(criteriaBuilder.notEqual(root.get("tuitionType"), "무료")); // 유료는 가격으로 저장되어있기에, 무료가 아닌 경우
                }
            }

            // 참여 시간 필터
            if (participationTime != null) {
                boolean isFullTime = "full-time".equalsIgnoreCase(participationTime);
                predicates.add(criteriaBuilder.equal(root.get("participationTime"), isFullTime ? ParticipationTime.FULL_TIME.getDatabaseName() : ParticipationTime.PART_TIME.getDatabaseName()));
            }

            // 선발 절차 필터
            if (selectionProcedure != null) {
                boolean isCodingTestExempt = true;

                if ("easy".equalsIgnoreCase(selectionProcedure)){ // 코딩테스트 없음 (false)
                    isCodingTestExempt = false;
                }

                predicates.add(criteriaBuilder.equal(root.get("codingTestExempt"), isCodingTestExempt));
            }

            // subCourse 필터
            if (subCourseId != null){
                predicates.add(criteriaBuilder.equal(root.get("subCourseEntity").get("id"), subCourseId));
            }

            query.where(predicates.toArray(new Predicate[0])); // 생성한 필터링조건들을 모두 연결

            // 정렬 기준에 따른 쿼리 구성
            if (sort != null) {
                switch (sort) {
                    case "deadline":
                        query.orderBy(criteriaBuilder.asc(root.get("closingDate")));
                        break;
                    case "startDate":
                        query.orderBy(criteriaBuilder.asc(root.get("startDate")));
                        break;
                    case "lowCost":
                        query.orderBy(criteriaBuilder.asc(root.get("tuitionType"))); // 문자열 - 숫자비교시, 문자열을 더 앞이라 생각하고 정렬함
                        break;
                    case "highCost":
                        query.orderBy(criteriaBuilder.desc(root.get("tuitionType")));
                        break;
                    case "shortDuration":
                        query.orderBy(criteriaBuilder.asc(criteriaBuilder.diff(root.get("endDate"), root.get("startDate"))));
                        break;
                    case "longDuration":
                        query.orderBy(criteriaBuilder.desc(criteriaBuilder.diff(root.get("endDate"), root.get("startDate"))));
                        break;
                    default:
                        // 기본 정렬 기준 (정렬 없음)
                        break;
                }
            }

            return query.getRestriction();
        });

        // 정렬 기준에 따라 쿼리 구성
        List<CourseEntity> courseEntities = courseJpaRepository.findAll(specification); // findAll을 할 때 조건식 추가
        return CourseConverter.toCourseList(courseEntities);
    }

    // html 값 - DB 값 매핑함수
    private String mapLocation(String location) {
        if (location != null) {
            switch (location) {
                case "seoul":
                    return "서울";
                case "busan":
                    return "부산";
                default:
                    return null;
            }
        }
        return null;
    }

}