package likelion.eight.user;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.user.enums.GenderType;
import likelion.eight.user.enums.RoleType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter @Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email; //TODO 패턴 검증필요

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String phoneNumber; //TODO 패턴 검증필요

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType genderType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Lob
    private byte[] image;
}
