package likelion.eight.domain.bootcamp.controller.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BootcampCreateRequest {
    private String name; // 교육기관명

    private String description; // 교육기관 한줄소개란

    private String location; // 위치

    private String url; // 교육기관 홈페이지 위치

    private MultipartFile file; // 교육기관 로고 이미지
}
