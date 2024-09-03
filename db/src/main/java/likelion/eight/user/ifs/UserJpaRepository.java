package likelion.eight.user.ifs;

import likelion.eight.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findByPhoneNumberAndName(String phoneNumber, String name);

//    --- redis
    List<UserEntity> findAllByNameIn(List<String> names);
}
