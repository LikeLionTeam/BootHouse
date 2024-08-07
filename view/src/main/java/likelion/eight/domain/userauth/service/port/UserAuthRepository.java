package likelion.eight.domain.userauth.service.port;

import likelion.eight.domain.userauth.model.UserAuth;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserAuthRepository {

    UserAuth save(UserAuth userAuth);

    UserAuth getById(long id);

    Optional<UserAuth> findById(long id);

    Page<UserAuth> findAll(int page, int size);

    void delete(UserAuth userAuth);
}
