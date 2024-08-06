package likelion.eight.domain.bootcamp.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Bootcamp {
    private final Long id;

    private final String name; // 교육기관명

    private final String logo; // 교육기관 로고 이미지

    private final String description; // 교육기관 한줄 소개란

    private final String location; // 교육기관 위치

    private final String url; // 교육기관 홈페이지 url

    @Builder
    public Bootcamp(Long id, String name, String logo, String description, String location, String url) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.location = location;
        this.url = url;
    }
}
