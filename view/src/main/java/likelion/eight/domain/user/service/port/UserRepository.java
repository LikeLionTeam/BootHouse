package likelion.eight.domain.user.service.port;

import likelion.eight.domain.user.model.User;
import likelion.eight.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User getById(long id);

    Optional<User> findById(long id);

    Optional<User> findByPhoneAndName(String phoneNumber, String name);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
