package likelion.eight.SubCourseEntity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import likelion.eight.BaseTimeEntity;
import likelion.eight.category.CategoryEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "sub_courses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SubCourseEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_course_id")
    private Long id;

    @Column(length = 255, nullable = false)
    private String name; // 세부 과정명 (예: 웹풀스택, 백엔드)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity; // 세부과정명(백엔드)는 하나의 카테고리에 속한다(웹에만 속한다, 동시에 모바일도 속할 순 없음)
}
