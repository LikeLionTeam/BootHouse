package likelion.eight.category.initializer;

import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
//public class CategoryInitData implements CommandLineRunner {
//
//    @Autowired private CategoryJpaRepository repository;
//    @Override
//    public void run(String... args) throws Exception {
//        CategoryEntity category = CategoryEntity.builder()
//                .name("웹개발")
//                .build();
//
//        CategoryEntity category2 = CategoryEntity.builder()
//                .name("모바일")
//                .build();
//
//        CategoryEntity category3 = CategoryEntity.builder()
//                .name("데이터·AI")
//                .build();
//
//        CategoryEntity category4 = CategoryEntity.builder()
//                .name("클라우드·보안")
//                .build();
//
//        CategoryEntity category5 = CategoryEntity.builder()
//                .name("IoT·임베디드·반도체")
//                .build();
//
//        CategoryEntity category6 = CategoryEntity.builder()
//                .name("게임·블록체인")
//                .build();
//
//        CategoryEntity category7 = CategoryEntity.builder()
//                .name("기획·마케팅·기타")
//                .build();
//
//        CategoryEntity category8 = CategoryEntity.builder()
//                .name("디자인·3D")
//                .build();
//
//        CategoryEntity category9 = CategoryEntity.builder()
//                .name("프로젝트·취준·창업")
//                .build();
//
//        repository.save(category);
//        repository.save(category2);
//        repository.save(category3);
//        repository.save(category4);
//        repository.save(category5);
//        repository.save(category6);
//        repository.save(category7);
//        repository.save(category8);
//        repository.save(category9);
//    }
//}
