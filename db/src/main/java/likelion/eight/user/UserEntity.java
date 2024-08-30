package likelion.eight.user;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import likelion.eight.userCourse.UserCourseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
@Getter @Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    @Column(name = "certification_code")
    private String certificationCode;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseEntity> courses = new ArrayList<>();

    public void addCourse(UserCourseEntity course) {
        if (courses == null) {
            courses = new ArrayList<>();  // 이 줄은 기본적으로 필요하지 않지만, 안전하게 추가
        }
        courses.add(course);
        course.setUserEntity(this);
    }

}
