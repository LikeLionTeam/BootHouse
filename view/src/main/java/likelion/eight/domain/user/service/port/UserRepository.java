package likelion.eight.domain.user.service.port;

import likelion.eight.domain.user.model.User;

public interface UserRepository {
    User save(User user);
}
