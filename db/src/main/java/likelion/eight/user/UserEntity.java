package likelion.eight.user;

import jakarta.persistence.*;
import likelion.eight.BaeTimeEntity;
import likelion.eight.profile.ProfileEntity;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaeTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profileEntity;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber; //TODO 패턴 검증필요
}
