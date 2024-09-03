package likelion.eight.certificationirequest;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.certificationirequest.enums.AuthRequestType;
import likelion.eight.course.CourseEntity;
import likelion.eight.user.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_authorizations")
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthRequestType authRequestType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthRequestStatus authRequestStatus;

    private LocalDateTime determined_at;

    @Lob
    private byte[] image;

    @Column(length = 255)
    private String imageUrl;
}
