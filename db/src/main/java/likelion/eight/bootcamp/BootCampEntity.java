package likelion.eight.bootcamp;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.course.CourseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bootcamps")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BootCampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bootcamp_id")
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255)
    private String logo; // 부트캠프 로고 이미지

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String location;

    @Column(nullable = false, name = "online_offline")
    private Boolean onlineOffline; // Boolean 타입으로 변경

    @Column(precision = 3, scale = 2) // 가능값 : 9.99, 0.01, 불가능값: 10.00
    private BigDecimal averageRating;

    @Column(nullable = false, name = "view_counts")
    private Integer viewCounts;

    @OneToMany(mappedBy = "bootCampEntity", cascade = CascadeType.ALL)
    private List<CourseEntity> courseEntities = new ArrayList<>();

    // 연관관계 메서드
    public void addCourse(CourseEntity courseEntity){
        courseEntities.add(courseEntity);
        courseEntity.setBootCampEntity(this);
    }
}
