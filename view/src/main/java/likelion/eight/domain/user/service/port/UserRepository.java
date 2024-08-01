package likelion.eight.domain.user.service.port;

import likelion.eight.domain.user.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User getById(long id);

    Optional<User> findById(long id);
}
