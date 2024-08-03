package likelion.eight.course.initializer;

import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.bootcamp.ifs.BootCampJpaRepository;
import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import likelion.eight.course.CourseEntity;
import likelion.eight.course.ParticipationTime;
import likelion.eight.course.ifs.CourseJpaRepository;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Component
public class CourseInitData implements CommandLineRunner {
    //@Autowired
    private CourseJpaRepository courseRepository;

    //@Autowired
    private BootCampJpaRepository bootCampRepository;

    //@Autowired
    private CategoryJpaRepository categoryRepository;
    @Override
    public void run(String... args) throws Exception {
        BootCampEntity bootCamp = bootCampRepository.findById(1L).get();
        CategoryEntity category = categoryRepository.findById(7L).get();

        CourseEntity course = CourseEntity.builder()
                .bootcampEntity(bootCamp)
                .categoryEntity(category)
                .name("UXUI 디자인 스쿨 3기")
                .startDate(LocalDate.of(2024, 8, 12))
                .endDate(LocalDate.of(2024, 12, 23))
                .closingDate(LocalDateTime.of(2024, 8,5,23,0))
                .codingTestExempt(false)
                .cardRequirement(true)
                .onlineOffline(true)
                //.location() -- 온라인이라 무시
                .tuitionType("무료")
                .summary("온라인, 풀타임으로 진행되는 KDT(무료) UI/UX디자인 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
                .participationTime(ParticipationTime.FULL_TIME)
                .averageRating(null)
                .viewCounts(0) //-- 평점이랑 조회수는 우선 null, 0 초기값으로
                .maxParticipants(60)
                .build();

        courseRepository.save(course);
    }
}
