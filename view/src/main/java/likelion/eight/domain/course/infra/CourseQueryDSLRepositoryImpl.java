package likelion.eight.domain.course.infra;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import likelion.eight.course.ParticipationTime;
import likelion.eight.course.QCourseEntity;
import likelion.eight.domain.course.controller.model.CourseDto;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.controller.model.QCourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static likelion.eight.course.QCourseEntity.courseEntity;

@Repository
@RequiredArgsConstructor
public class CourseQueryDSLRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    // 필터조건에 맞는 코스 리스트 반환 (QueryDSL 사용)
    public Page<CourseDto> findCoursesByFiltersQueryDSL(Long categoryId,
                                                        CourseFilter courseFilter,
                                                        String sort,
                                                        String search,
                                                        Pageable pageable){
        QCourseEntity courseEntity = QCourseEntity.courseEntity;

        // QueryDSL의 경우, entity전체를 가져오는 것이 아니라 조회대상을 지정하고 싶을 때는 Projections을 사용함
        List<CourseDto> results = queryFactory
                .select(new QCourseDto(
                        courseEntity.id,
                        courseEntity.name,
                        courseEntity.subCourseEntity.name,
                        courseEntity.closingDate,
                        courseEntity.tuitionType,
                        courseEntity.onlineOffline,
                        courseEntity.location,
                        courseEntity.startDate,
                        courseEntity.endDate,
                        courseEntity.participationTime,
                        courseEntity.codingTestExempt
                ))
                .from(courseEntity)
// 이전 코드에서는, toCourse로 컨버터에서 변환하면서 N+1문제가 발생했는데, 여기서는 바로 CourseDto로 받으니까, fetch join을 할 필요가 없다!
//                .leftJoin(courseEntity.bootcampEntity).fetchJoin()
//                .leftJoin(courseEntity.categoryEntity).fetchJoin()
//                .leftJoin(courseEntity.subCourseEntity).fetchJoin()
                .where(
                        // 각 표현식(BooleanExpression)이 true인 경우에만, 해당조건이 쿼리에 포함됨 (null이면 포함안됨)
                        categoryEq(categoryId),
                        onlineOfflineEq(courseFilter.getOnlineOffline()),
                        locationEq(courseFilter.getLocation()),
                        tuitionTypeEq(courseFilter.getCost()),
                        participationTimeEq(courseFilter.getParticipationTime()),
                        selectionProcedureEq(courseFilter.getSelectionProcedure()),
                        subCourseEq(courseFilter.getSubCourse()),
                        searchLike(search)
                )
                .orderBy(sortBy(sort)) // 이 부분에서 null인 경우, 기본정렬 반환되도록 처리
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // 리스트로 결과 반환

        // 전체 개수를 위한 카운트 쿼리
        long total = queryFactory
                .select(courseEntity.count())
                .from(courseEntity)
                .where( // 동일한 필터링 조건을 걸어야, courseEntity의 전체개수(페이지로 offset, limit 제한을 두지 않은)를 알 수 있기에.
                        categoryEq(categoryId),
                        onlineOfflineEq(courseFilter.getOnlineOffline()),
                        locationEq(courseFilter.getLocation()),
                        tuitionTypeEq(courseFilter.getCost()),
                        participationTimeEq(courseFilter.getParticipationTime()),
                        selectionProcedureEq(courseFilter.getSelectionProcedure()),
                        subCourseEq(courseFilter.getSubCourse()),
                        searchLike(search)
                )
                .fetchOne(); // 단건 조회 반환

        return new PageImpl<>(results, pageable, total);
    }

    // Category 필터 조건
    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null ? courseEntity.categoryEntity.id.eq(categoryId) : null;
    }

    // 온라인/오프라인 필터 조건
    private BooleanExpression onlineOfflineEq(String onlineOffline) {
        if (onlineOffline != null) {
            boolean isOnline = "online".equalsIgnoreCase(onlineOffline);
            return courseEntity.onlineOffline.eq(isOnline);
        }
        return null;
    }

    // 지역 필터 조건
    private BooleanExpression locationEq(String location) {
        String mappedLocation = mapLocation(location);
        return mappedLocation != null ? courseEntity.location.eq(mappedLocation) : null;
    }

    // 비용 필터 조건
    private BooleanExpression tuitionTypeEq(String cost) {
        if (cost != null) {
            if ("free".equalsIgnoreCase(cost)) {
                return courseEntity.tuitionType.eq("무료");
            } else if ("paid".equalsIgnoreCase(cost)) {
                return courseEntity.tuitionType.ne("무료");
            }
        }
        return null;
    }

    // 참여 시간 필터 조건
    private BooleanExpression participationTimeEq(String participationTime) {
        if (participationTime != null) {
            boolean isFullTime = "full-time".equalsIgnoreCase(participationTime);
            return courseEntity.participationTime.eq(
                    isFullTime ? ParticipationTime.FULL_TIME : ParticipationTime.PART_TIME
            );
        }
        return null;
    }

    // 선발 절차 필터 조건
    private BooleanExpression selectionProcedureEq(String selectionProcedure) {
        if (selectionProcedure != null) {
            boolean isCodingTestExempt = "hard".equalsIgnoreCase(selectionProcedure);
            return courseEntity.codingTestExempt.eq(isCodingTestExempt);
        }
        return null;
    }

    // SubCourse 필터 조건
    private BooleanExpression subCourseEq(Long subCourseId) {
        return subCourseId != null ? courseEntity.subCourseEntity.id.eq(subCourseId) : null;
    }

    // 검색어 필터 조건 -- like 'name%' --> 한쪽에만 % 붙임
    private BooleanExpression searchLike(String search) {
        return search != null ? courseEntity.name.startsWith(search) : null;
    }

    // 정렬 조건 추가
    private OrderSpecifier<?> sortBy(String sort) {
        if (sort != null) {
            switch (sort) {
                case "deadline":
                    return courseEntity.closingDate.asc();
                case "startDate":
                    return courseEntity.startDate.asc();
                case "lowCost":
                    return courseEntity.tuitionType.asc();
                case "highCost":
                    return courseEntity.tuitionType.desc();
                case "shortDuration":
                    NumberExpression<Integer> durationAsc = Expressions.numberTemplate(
                            Integer.class,
                            "EXTRACT(EPOCH FROM {0}) - EXTRACT(EPOCH FROM {1})",
                            courseEntity.endDate,
                            courseEntity.startDate
                    );
                    return durationAsc.asc();
                case "longDuration":
                    NumberExpression<Integer> durationDesc = Expressions.numberTemplate(
                            Integer.class,
                            "EXTRACT(EPOCH FROM {0}) - EXTRACT(EPOCH FROM {1})",
                            courseEntity.endDate,
                            courseEntity.startDate
                    );
                    return durationDesc.desc();
            }
        }
        // sort가 null이면, 기본정렬 설정
        return courseEntity.closingDate.asc();
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

