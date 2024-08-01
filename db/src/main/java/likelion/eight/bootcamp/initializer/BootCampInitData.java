package likelion.eight.bootcamp.initializer;

import likelion.eight.bootcamp.ifs.BootCampJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootCampInitData implements CommandLineRunner {

    @Autowired
    private BootCampJpaRepository repository;
    @Override
    public void run(String... args) throws Exception {
        BootCampEntity bootCamp = BootCampEntity.builder()
                .name("멋쟁이사자처럼")
                .description("IT분야의 TECH 인재를 키우는 교육 커뮤니티, 멋쟁이사자처럼!")
                .location("서울특별시 종로구 종로3길 17 D타워, 16~17층")
                .url("https://techit.education")
                .build();

        BootCampEntity bootCamp2  = BootCampEntity.builder()
                .name("코드잇")
                .description("교육에 진심인 코드잇의 밀착관리 부트캠프, 스프린트")
                .location("서울특별시 중구 청계천로 100 시그니쳐타워 동관 10층")
                .url("https://www.codeit.kr")
                .build();

        BootCampEntity bootCamp3  = BootCampEntity.builder()
                .name("패스트캠퍼스")
                .description("우리는 지금 누군가의 인생을 바꾸는 일을 하고 있습니다")
                .location("서울특별시 강남구 테헤란로 231, 센터필드 WEST 6층, 7층")
                .url("https://fastcampus.co.kr")
                .build();

        BootCampEntity bootCamp4  = BootCampEntity.builder()
                .name("넥스트러너스")
                .description("모두가 능동적으로 삶을 살아가는 세상")
                .location("서울특별시 서초구 강남대로99길 45 엠빌딩 6층")
                .url("https://nextrunners.co.kr/")
                .build();

        repository.save(bootCamp);
        repository.save(bootCamp2);
        repository.save(bootCamp3);


        // 확인용 로그
        System.out.println("Saved BootCampEntity: " + bootCamp);
    };
}


