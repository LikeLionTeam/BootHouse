package likelion.eight.SubCourseEntity.initializer;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.SubCourseEntity.ifs.SubCourseJpaRepository;
import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
//public class SubCourseInitData implements CommandLineRunner {
//    @Autowired
//    SubCourseJpaRepository subCourseRepository;
//
//    @Autowired
//    CategoryJpaRepository categoryRepository;
//    @Override
//    public void run(String... args) throws Exception {
//        // 웹개발
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
//        subCourseRepository.save(webSubCourse1);
//        subCourseRepository.save(webSubCourse2);
//        subCourseRepository.save(webSubCourse3);
//        subCourseRepository.save(webSubCourse4);
//
//// 모바일
//        CategoryEntity mobileCategory = categoryRepository.findByName("모바일");
//        SubCourseEntity mobileSubCourse1 = SubCourseEntity.builder()
//                .name("안드로이드")
//                .categoryEntity(mobileCategory)
//                .build();
//        SubCourseEntity mobileSubCourse2 = SubCourseEntity.builder()
//                .name("iOS")
//                .categoryEntity(mobileCategory)
//                .build();
//        SubCourseEntity mobileSubCourse3 = SubCourseEntity.builder()
//                .name("앱개발")
//                .categoryEntity(mobileCategory)
//                .build();
//        subCourseRepository.save(mobileSubCourse1);
//        subCourseRepository.save(mobileSubCourse2);
//        subCourseRepository.save(mobileSubCourse3);
//
//// 데이터·AI
//        CategoryEntity dataAICategory = categoryRepository.findByName("데이터·AI");
//        SubCourseEntity dataAISubCourse1 = SubCourseEntity.builder()
//                .name("DBA")
//                .categoryEntity(dataAICategory)
//                .build();
//        SubCourseEntity dataAISubCourse2 = SubCourseEntity.builder()
//                .name("데이터분석")
//                .categoryEntity(dataAICategory)
//                .build();
//        SubCourseEntity dataAISubCourse3 = SubCourseEntity.builder()
//                .name("데이터엔지니어링")
//                .categoryEntity(dataAICategory)
//                .build();
//        SubCourseEntity dataAISubCourse4 = SubCourseEntity.builder()
//                .name("AI/ML")
//                .categoryEntity(dataAICategory)
//                .build();
//        subCourseRepository.save(dataAISubCourse1);
//        subCourseRepository.save(dataAISubCourse2);
//        subCourseRepository.save(dataAISubCourse3);
//        subCourseRepository.save(dataAISubCourse4);
//
//// 클라우드·보안
//        CategoryEntity cloudCategory = categoryRepository.findByName("클라우드·보안");
//        SubCourseEntity cloudSubCourse1 = SubCourseEntity.builder()
//                .name("클라우드")
//                .categoryEntity(cloudCategory)
//                .build();
//        SubCourseEntity cloudSubCourse2 = SubCourseEntity.builder()
//                .name("데브옵스")
//                .categoryEntity(cloudCategory)
//                .build();
//        SubCourseEntity cloudSubCourse3 = SubCourseEntity.builder()
//                .name("인프라")
//                .categoryEntity(cloudCategory)
//                .build();
//        SubCourseEntity cloudSubCourse4 = SubCourseEntity.builder()
//                .name("보안")
//                .categoryEntity(cloudCategory)
//                .build();
//        subCourseRepository.save(cloudSubCourse1);
//        subCourseRepository.save(cloudSubCourse2);
//        subCourseRepository.save(cloudSubCourse3);
//        subCourseRepository.save(cloudSubCourse4);
//
//// IoT·임베디드·반도체
//        CategoryEntity iotCategory = categoryRepository.findByName("IoT·임베디드·반도체");
//        SubCourseEntity iotSubCourse1 = SubCourseEntity.builder()
//                .name("로보틱스")
//                .categoryEntity(iotCategory)
//                .build();
//        SubCourseEntity iotSubCourse2 = SubCourseEntity.builder()
//                .name("임베디드")
//                .categoryEntity(iotCategory)
//                .build();
//        SubCourseEntity iotSubCourse3 = SubCourseEntity.builder()
//                .name("IoT")
//                .categoryEntity(iotCategory)
//                .build();
//        SubCourseEntity iotSubCourse4 = SubCourseEntity.builder()
//                .name("반도체")
//                .categoryEntity(iotCategory)
//                .build();
//        subCourseRepository.save(iotSubCourse1);
//        subCourseRepository.save(iotSubCourse2);
//        subCourseRepository.save(iotSubCourse3);
//        subCourseRepository.save(iotSubCourse4);
//
//// 게임·블록체인
//        CategoryEntity gameCategory = categoryRepository.findByName("게임·블록체인");
//        SubCourseEntity gameSubCourse1 = SubCourseEntity.builder()
//                .name("메타버스")
//                .categoryEntity(gameCategory)
//                .build();
//        SubCourseEntity gameSubCourse2 = SubCourseEntity.builder()
//                .name("게임")
//                .categoryEntity(gameCategory)
//                .build();
//        SubCourseEntity gameSubCourse3 = SubCourseEntity.builder()
//                .name("블록체인")
//                .categoryEntity(gameCategory)
//                .build();
//        SubCourseEntity gameSubCourse4 = SubCourseEntity.builder()
//                .name("AR/VR")
//                .categoryEntity(gameCategory)
//                .build();
//        subCourseRepository.save(gameSubCourse1);
//        subCourseRepository.save(gameSubCourse2);
//        subCourseRepository.save(gameSubCourse3);
//        subCourseRepository.save(gameSubCourse4);
//
//// 기획·마케팅·기타
//        CategoryEntity planCategory = categoryRepository.findByName("기획·마케팅·기타");
//        SubCourseEntity planSubCourse1 = SubCourseEntity.builder()
//                .name("RPA")
//                .categoryEntity(planCategory)
//                .build();
//        SubCourseEntity planSubCourse2 = SubCourseEntity.builder()
//                .name("PM/기획")
//                .categoryEntity(planCategory)
//                .build();
//        SubCourseEntity planSubCourse3 = SubCourseEntity.builder()
//                .name("마케팅")
//                .categoryEntity(planCategory)
//                .build();
//        subCourseRepository.save(planSubCourse1);
//        subCourseRepository.save(planSubCourse2);
//        subCourseRepository.save(planSubCourse3);
//
//// 디자인·3D
//        CategoryEntity designCategory = categoryRepository.findByName("디자인·3D");
//        SubCourseEntity designSubCourse1 = SubCourseEntity.builder()
//                .name("UXUI/디자인")
//                .categoryEntity(designCategory)
//                .build();
//        SubCourseEntity designSubCourse2 = SubCourseEntity.builder()
//                .name("영상")
//                .categoryEntity(designCategory)
//                .build();
//        SubCourseEntity designSubCourse3 = SubCourseEntity.builder()
//                .name("3D")
//                .categoryEntity(designCategory)
//                .build();
//        subCourseRepository.save(designSubCourse1);
//        subCourseRepository.save(designSubCourse2);
//        subCourseRepository.save(designSubCourse3);
//
//// 프로젝트·취준·창업
//        CategoryEntity projectCategory = categoryRepository.findByName("프로젝트·취준·창업");
//        SubCourseEntity projectSubCourse1 = SubCourseEntity.builder()
//                .name("창업")
//                .categoryEntity(projectCategory)
//                .build();
//        SubCourseEntity projectSubCourse2 = SubCourseEntity.builder()
//                .name("노코드")
//                .categoryEntity(projectCategory)
//                .build();
//        SubCourseEntity projectSubCourse3 = SubCourseEntity.builder()
//                .name("프로젝트")
//                .categoryEntity(projectCategory)
//                .build();
//        SubCourseEntity projectSubCourse4 = SubCourseEntity.builder()
//                .name("취준멘토링")
//                .categoryEntity(projectCategory)
//                .build();
//        subCourseRepository.save(projectSubCourse1);
//        subCourseRepository.save(projectSubCourse2);
//        subCourseRepository.save(projectSubCourse3);
//        subCourseRepository.save(projectSubCourse4);
//
//    }
//}