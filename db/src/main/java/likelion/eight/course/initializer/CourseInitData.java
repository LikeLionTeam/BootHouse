package likelion.eight.course.initializer;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.SubCourseEntity.ifs.SubCourseJpaRepository;
import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.bootcamp.ifs.BootCampJpaRepository;
import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import likelion.eight.course.CourseEntity;
import likelion.eight.course.ParticipationTime;
import likelion.eight.course.ifs.CourseJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;

@Component
@Slf4j
public class CourseInitData implements CommandLineRunner {
    @Autowired
    private CourseJpaRepository courseRepository;

    @Autowired
    private BootCampJpaRepository bootCampRepository;

    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Autowired
    private SubCourseJpaRepository subCourseRepository;

    @Override
    public void run(String... args) throws Exception {
      
        // 멋쟁이사자처럼, 웹개발 카테고리 코스 여러개
//        BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("디자인·3D");
//        SubCourseEntity subCourse = subCourseRepository.findByName("UXUI/디자인");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("UXUI 디자인 스쿨 3기")
//                .startDate(LocalDate.of(2024, 8, 12))
//                .endDate(LocalDate.of(2024, 12, 23))
//                .closingDate(LocalDateTime.of(2024, 8,5,23,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 풀타임으로 진행되는 KDT(무료) UI/UX디자인 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(60)
//                .build();
//
//        courseRepository.save(course);

//                BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("백엔드");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("Java 첫걸음 시작하기")
//                .startDate(LocalDate.of(2024, 8, 5))
//                .endDate(LocalDate.of(2024, 9, 15))
//                .closingDate(LocalDateTime.of(2024, 8,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 백엔드 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(-1) // 정원없음
//                .build();
//
//        courseRepository.save(course);

//
//        BootCampEntity bootCamp = bootCampRepository.findByName("코드잇");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("프론트앤드");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("HTML/CSS/JS 기초 첫걸음")
//                .startDate(LocalDate.of(2024, 10, 5))
//                .endDate(LocalDate.of(2024, 12, 15))
//                .closingDate(LocalDateTime.of(2024, 10,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 프론트앤드 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(50) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//        BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("퍼블리싱");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("웹퍼블리싱의 모든 것")
//                .startDate(LocalDate.of(2024, 11, 5))
//                .endDate(LocalDate.of(2024, 12, 29))
//                .closingDate(LocalDateTime.of(2024, 11,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 퍼블리싱 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(30) // 정원없음
//                .build();
//
//        courseRepository.save(course);

//        BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("퍼블리싱");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("웹퍼블리싱 기초")
//                .startDate(LocalDate.of(2024, 10, 15))
//                .endDate(LocalDate.of(2025, 6, 19))
//                .closingDate(LocalDateTime.of(2024, 10,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("30")
//                .summary("온라인, 풀타임으로 진행되는 유료(30) 퍼블리싱 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(30) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//        BootCampEntity bootCamp = bootCampRepository.findByName("더조은컴퓨터아카데미");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("웹 풀스택");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("IT 부트캠프 프로젝트 기반 AWS 자바(스프링부트&리액트) 풀스택 개발자 양성 7회차")
//                .startDate(LocalDate.of(2024, 8, 19))
//                .endDate(LocalDate.of(2025, 3, 4))
//                .closingDate(LocalDateTime.of(2024, 8, 30, 0,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(false)
//                .location("서울") // -- 오프라인이라, 명시함
//                .tuitionType("무료")
//                .summary("서울에서 오프라인, 풀타임으로 진행되는 KDT(무료) 웹풀스택 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(20) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//         두번째 카테고리 :: 모바일
//        BootCampEntity bootCamp = bootCampRepository.findByName("코드잇");
//        CategoryEntity category = categoryRepository.findByName("모바일");
//        SubCourseEntity subCourse = subCourseRepository.findByName("iOS");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("생성형 AI활용 클라우드 기반 iOS 앱개발자 2024")
//                .startDate(LocalDate.of(2024, 8, 19))
//                .endDate(LocalDate.of(2024, 12, 11))
//                .closingDate(LocalDateTime.of(2024, 8, 18, 0,0))
//                .codingTestExempt(true)
//                .cardRequirement(false)
//                .onlineOffline(false)
//                .location("서울특별시 중구 청계천로 100 시그니쳐타워") // -- 오프라인이라, 명시함
//                .tuitionType("10") // -- 10만원
//                .summary("서울에서 오프라인, 풀타임으로 진행되는 유료(10만원) iOS 부트캠프입니다. 선발절차에 코딩테스트는 있습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(16) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//
//         세번째 카테고리 :: 데이터 AI
//        BootCampEntity bootCamp = bootCampRepository.findByName("패스트캠퍼스");
//        CategoryEntity category = categoryRepository.findByName("데이터·AI");
//        SubCourseEntity subCourse = subCourseRepository.findByName("데이터분석");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("데이터분석 부트캠프 16기")
//
//                .startDate(LocalDate.of(2024, 9, 19))
//                .endDate(LocalDate.of(2025, 12, 10))
//                .closingDate(LocalDateTime.of(2024, 9, 4, 0,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location("서울") // -- 오프라인이라, 명시함
//                .tuitionType("무료") // -- 10만원
//                .summary("온라인, 풀타임으로 진행되는 KDT(무료) 데이터분석 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(40) // 정원없음
//                .build();
//
//        courseRepository.save(course);

//        BootCampEntity bootCamp = bootCampRepository.findById(1L).get();
//        CategoryEntity category = categoryRepository.findById(7L).get();
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .name("UXUI 디자인 스쿨 3기")
//                .startDate(LocalDate.of(2024, 8, 12))
//                .endDate(LocalDate.of(2024, 12, 23))
//                .closingDate(LocalDateTime.of(2024, 8,12,23,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 풀타임으로 진행되는 KDT(무료) UI/UX디자인 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .averageRating(null)
//                .viewCounts(0) //-- 평점이랑 조회수는 우선 null, 0 초기값으로
//                .maxParticipants(60)
//                .build();
//
//
//        courseRepository.save(course);


//        BootCampEntity bootCamp = bootCampRepository.findByName("코드잇");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("프론트앤드");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("HTML/CSS/JS 기초 첫걸음")
//                .startDate(LocalDate.of(2024, 10, 5))
//                .endDate(LocalDate.of(2024, 12, 15))
//                .closingDate(LocalDateTime.of(2024, 10,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 프론트앤드 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(50) // 정원없음
//                .build();
//
//        courseRepository.save(course);


//// 외래키 데이터 가져오기 (랜덤데이터셋)
//        log.info("Course 2.5만건 데이터셋 초기화!!");
//        List<BootCampEntity> bootCamps = bootCampRepository.findAll();
//        List<CategoryEntity> categories = categoryRepository.findAll();
//        List<SubCourseEntity> subCourses = subCourseRepository.findAll();
//
//        Random random = new Random();
//
//// SubCourse와 Category 간의 매핑 규칙 설정
//        Map<Integer, Integer> subCourseCategoryMap = Map.ofEntries(
//                new SimpleEntry<>(1, 1),
//                new SimpleEntry<>(2, 1),
//                new SimpleEntry<>(3, 1),
//                new SimpleEntry<>(4, 1),
//                new SimpleEntry<>(5, 2),
//                new SimpleEntry<>(6, 2),
//                new SimpleEntry<>(7, 2),
//                new SimpleEntry<>(8, 3),
//                new SimpleEntry<>(9, 3),
//                new SimpleEntry<>(10, 3),
//                new SimpleEntry<>(11, 3),
//                new SimpleEntry<>(12, 4),
//                new SimpleEntry<>(13, 4),
//                new SimpleEntry<>(14, 4),
//                new SimpleEntry<>(15, 4),
//                new SimpleEntry<>(16, 5),
//                new SimpleEntry<>(17, 5),
//                new SimpleEntry<>(18, 5),
//                new SimpleEntry<>(19, 5),
//                new SimpleEntry<>(24, 7),
//                new SimpleEntry<>(25, 7),
//                new SimpleEntry<>(26, 7),
//                new SimpleEntry<>(27, 8),
//                new SimpleEntry<>(28, 8),
//                new SimpleEntry<>(29, 8),
//                new SimpleEntry<>(30, 9),
//                new SimpleEntry<>(31, 9),
//                new SimpleEntry<>(32, 9),
//                new SimpleEntry<>(33, 9)
//        );
//
//// subCourseId를 key로 하는 Category 리스트 매핑
//        Map<Integer, List<SubCourseEntity>> categorizedSubCourses = subCourses.stream()
//                .filter(sc -> subCourseCategoryMap.containsKey(sc.getId().intValue()))
//                .collect(Collectors.groupingBy(sc -> subCourseCategoryMap.get(sc.getId().intValue())));
//
//        for (int i = 0; i < 25000; i++) { // 랜덤데이터셋 개수 설정 !!
//            BootCampEntity randomBootCamp = bootCamps.get(random.nextInt(bootCamps.size()));
//
//            // 랜덤 카테고리 선택 및 해당 카테고리에 속하는 SubCourse 선택
//            int randomCategoryId = random.nextInt(9) + 1; // Category ID는 1~9 사이
//            CategoryEntity randomCategory = categories.stream()
//                    .filter(c -> c.getId().intValue() == randomCategoryId)
//                    .findFirst().orElse(null);
//
//            List<SubCourseEntity> availableSubCourses = categorizedSubCourses.get(randomCategoryId);
//
//            if (availableSubCourses == null || availableSubCourses.isEmpty()) {
//                System.out.println("No available SubCourseEntity for Category ID " + randomCategoryId);
//                continue; // 해당 카테고리에 매핑된 SubCourse가 없으면 건너뜀
//            }
//
//            SubCourseEntity randomSubCourse = availableSubCourses.get(random.nextInt(availableSubCourses.size()));
//
//            // 수업시작일
//            LocalDate startDate = LocalDate.now().plusDays(random.nextInt(30)); // 현재시점으로부터 30일 내의 랜덤 날짜
//            // 수업종료일
//            LocalDate endDate = startDate.plusDays(30 + random.nextInt(30)); // 시작일 기준 30~60일 후 종료일 설정
//            // 모집마감일 - 수업시작일 기준 1~7일 전으로 설정
//            LocalDateTime closingDate = startDate.minusDays(1 + random.nextInt(7)).atStartOfDay();
//
//            boolean isOnline = random.nextBoolean();
//            String location = isOnline ? null : (random.nextBoolean() ? "서울" : "부산");
//
//            CourseEntity course = CourseEntity.builder()
//                    .bootcampEntity(randomBootCamp)
//                    .categoryEntity(randomCategory)
//                    .subCourseEntity(randomSubCourse)
//                    .name("Course " + String.format("%05d", i))
//                    .startDate(startDate)
//                    .endDate(endDate)
//                    .closingDate(closingDate)
//                    .codingTestExempt(random.nextBoolean())
//                    .cardRequirement(random.nextBoolean())
//                    .onlineOffline(isOnline)
//                    .location(location) // 온라인일 경우 null, 오프라인일 경우 Seoul 또는 Busan
//                    .tuitionType(random.nextBoolean() ? String.valueOf(10 + random.nextInt(91)) : "무료") // "무료" 혹은 10~100 사이의 금액
//                    .summary("This is a randomly generated course.")
//                    .participationTime(random.nextBoolean() ? ParticipationTime.FULL_TIME : ParticipationTime.PART_TIME)
//                    .maxParticipants(random.nextInt(10) == 0 ? -1 : random.nextInt(100)) // 10% 확률로 모집 정원 없음 (-1)
//                    .averageRating(BigDecimal.valueOf(random.nextDouble() * 5).setScale(2, RoundingMode.HALF_UP)) // 0.00 ~ 5.00 사이 평점
//                    .build();
//
//            courseRepository.save(course);
//
//        }

    }
}
