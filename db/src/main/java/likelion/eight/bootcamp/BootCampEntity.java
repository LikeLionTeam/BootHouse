package likelion.eight.bootcamp;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
    기관소개 페이지 -- 부트캠프 주관 교육기관 저장
 */
@Entity
@Table(name = "bootcamps")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BootCampEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bootcamp_id")
    private Long id;

    @Column(length = 255, nullable = false)
    private String name; // 교육기관명

    @Column(length = 255)
    private String logo; // 교육기관 로고 이미지

    @Column(columnDefinition = "TEXT")
    private String description; // 교육기관 한줄 소개란

    @Column(length = 255)
    private String location; // 교육기관 위치

    private String url; // 교육기관 홈페이지 url


}
