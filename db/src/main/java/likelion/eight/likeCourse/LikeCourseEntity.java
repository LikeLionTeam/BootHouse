package likelion.eight.likeCourse;


import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.course.CourseEntity;
import likelion.eight.user.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "like_courses")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCourseEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    public static LikeCourseEntity createLikeCourseEntity(UserEntity userEntity, CourseEntity courseEntity){
        LikeCourseEntity likeCourseEntity = new LikeCourseEntity();

        likeCourseEntity.userEntity = userEntity;
        likeCourseEntity.courseEntity = courseEntity;

        return likeCourseEntity;
    }


    @Builder
    public LikeCourseEntity(UserEntity userEntity, CourseEntity courseEntity) {
        this.userEntity = userEntity;
        this.courseEntity = courseEntity;
    }
}
