package likelion.eight.SubCourseEntity.initializer;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.SubCourseEntity.ifs.SubCourseJpaRepository;
import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SubCourseInitData implements CommandLineRunner {
    @Autowired
    SubCourseJpaRepository subCourseRepository;

    @Autowired
    CategoryJpaRepository categoryRepository;
    @Override
    public void run(String... args) throws Exception {
        // 웹개발
//        CategoryEntity webCategory = categoryRepository.findByName("웹개발");
//        SubCourseEntity webSubCourse1 = SubCourseEntity.builder()
//                .name("웹 풀스택")
//                .categoryEntity(webCategory)
//                .build();
//        SubCourseEntity webSubCourse2 = SubCourseEntity.builder()
//                .name("백엔드")
//                .categoryEntity(webCategory)
//                .build();
//        SubCourseEntity webSubCourse3 = SubCourseEntity.builder()
//                .name("프론트앤드")
//                .categoryEntity(webCategory)
//                .build();
//        SubCourseEntity webSubCourse4 = SubCourseEntity.builder()
//                .name("퍼블리싱")
//                .categoryEntity(webCategory)
//                .build();
//
//        subCourseRepository.save(webSubCourse1);
//        subCourseRepository.save(webSubCourse2);
//        subCourseRepository.save(webSubCourse3);
//        subCourseRepository.save(webSubCourse4);

//         모바일

//        CategoryEntity mobileCategory = categoryRepository.findByName("모바일");
//
//        SubCourseEntity mobileSubCourse1 = SubCourseEntity.builder()
//                .name("안드로이드")
//                .categoryEntity(mobileCategory)
//                .build();
//
//        SubCourseEntity mobileSubCourse2 = SubCourseEntity.builder()
//                .name("iOS")
//                .categoryEntity(mobileCategory)
//                .build();
//
//        SubCourseEntity mobileSubCourse3 = SubCourseEntity.builder()
//                .name("앱개발")
//                .categoryEntity(mobileCategory)
//                .build();
//
//        subCourseRepository.save(mobileSubCourse1);
//        subCourseRepository.save(mobileSubCourse2);
//        subCourseRepository.save(mobileSubCourse3);
//
//        // 데이터·AI
//        CategoryEntity DataAICategory = categoryRepository.findByName("데이터·AI");
//
//        SubCourseEntity DataAISubCourse1 = SubCourseEntity.builder()
//                .name("DBA")
//                .categoryEntity(DataAICategory)
//                .build();
//
//        SubCourseEntity DataAISubCourse2 = SubCourseEntity.builder()
//                .name("데이터분석")
//                .categoryEntity(DataAICategory)
//                .build();
//
//        SubCourseEntity DataAISubCourse3 = SubCourseEntity.builder()
//                .name("데이터엔지니어링")
//                .categoryEntity(DataAICategory)
//                .build();
//
//        SubCourseEntity DataAISubCourse4 = SubCourseEntity.builder()
//                .name("AI/ML")
//                .categoryEntity(DataAICategory)
//                .build();
//
//        subCourseRepository.save(DataAISubCourse1);
//        subCourseRepository.save(DataAISubCourse2);
//        subCourseRepository.save(DataAISubCourse3);
//        subCourseRepository.save(DataAISubCourse4);

        // 클라우드·보안

//        CategoryEntity CloudCategory = categoryRepository.findByName("클라우드·보안");
//
//        SubCourseEntity subCourseEntity1 = SubCourseEntity.builder()
//                .name("클라우드")
//                .categoryEntity(CloudCategory)
//                .build();
//
//        SubCourseEntity subCourseEntity2 = SubCourseEntity.builder()
//                .name("데브옵스")
//                .categoryEntity(CloudCategory)
//                .build();
//
//        SubCourseEntity subCourseEntity3 = SubCourseEntity.builder()
//                .name("인프라")
//                .categoryEntity(CloudCategory)
//                .build();
//
//        SubCourseEntity subCourseEntity4 = SubCourseEntity.builder()
//                .name("보안")
//                .categoryEntity(CloudCategory)
//                .build();
//
//        subCourseRepository.save(subCourseEntity1);
//        subCourseRepository.save(subCourseEntity2);
//        subCourseRepository.save(subCourseEntity3);
//        subCourseRepository.save(subCourseEntity4);

//         IoT·임베디드·반도체


//        CategoryEntity category = categoryRepository.findByName("IoT·임베디드·반도체");
//
//        SubCourseEntity subCourseEntity1 = SubCourseEntity.builder()
//                .name("로보틱스")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity2 = SubCourseEntity.builder()
//                .name("임베디드")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity3 = SubCourseEntity.builder()
//                .name("IoT")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity4 = SubCourseEntity.builder()
//                .name("반도체")
//                .categoryEntity(category)
//                .build();
//
//        subCourseRepository.save(subCourseEntity1);
//        subCourseRepository.save(subCourseEntity2);
//        subCourseRepository.save(subCourseEntity3);
//        subCourseRepository.save(subCourseEntity4);
//
//        // 게임·블록체인
//        CategoryEntity category = categoryRepository.findByName("게임·블록체인");
//
//        SubCourseEntity subCourseEntity1 = SubCourseEntity.builder()
//                .name("메타버스")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity2 = SubCourseEntity.builder()
//                .name("게임")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity3 = SubCourseEntity.builder()
//                .name("블록체인")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity4 = SubCourseEntity.builder()
//                .name("AR/VR")
//                .categoryEntity(category)
//                .build();
//
//        subCourseRepository.save(subCourseEntity1);
//        subCourseRepository.save(subCourseEntity2);
//        subCourseRepository.save(subCourseEntity3);
//        subCourseRepository.save(subCourseEntity4);

        // 기획·마케팅·기타

//        CategoryEntity category = categoryRepository.findByName("기획·마케팅·기타");
//
//        SubCourseEntity subCourseEntity1 = SubCourseEntity.builder()
//                .name("RPA")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity2 = SubCourseEntity.builder()
//                .name("PM/기획")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity3 = SubCourseEntity.builder()
//                .name("마케팅")
//                .categoryEntity(category)
//                .build();
//
//        subCourseRepository.save(subCourseEntity1);
//        subCourseRepository.save(subCourseEntity2);
//        subCourseRepository.save(subCourseEntity3);

       //  디자인·3D

//        CategoryEntity category = categoryRepository.findByName("디자인·3D");
//
//        SubCourseEntity subCourseEntity1 = SubCourseEntity.builder()
//                .name("UXUI/디자인")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity2 = SubCourseEntity.builder()
//                .name("영상")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity3 = SubCourseEntity.builder()
//                .name("3D")
//                .categoryEntity(category)
//                .build();
//
//        subCourseRepository.save(subCourseEntity1);
//        subCourseRepository.save(subCourseEntity2);
//        subCourseRepository.save(subCourseEntity3);
//
//        // 프로젝트·취준·창업
//
//        CategoryEntity category = categoryRepository.findByName("프로젝트·취준·창업");
//
//        SubCourseEntity subCourseEntity1 = SubCourseEntity.builder()
//                .name("창업")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity2 = SubCourseEntity.builder()
//                .name("노코드")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity3 = SubCourseEntity.builder()
//                .name("프로젝트")
//                .categoryEntity(category)
//                .build();
//
//        SubCourseEntity subCourseEntity4 = SubCourseEntity.builder()
//                .name("취준멘토링")
//                .categoryEntity(category)
//                .build();
//
//        subCourseRepository.save(subCourseEntity1);
//        subCourseRepository.save(subCourseEntity2);
//        subCourseRepository.save(subCourseEntity3);
//        subCourseRepository.save(subCourseEntity4);

    }
}