package likelion.eight.domain.userauth.infra;

import likelion.eight.certificationirequest.UserAuthEntity;
import likelion.eight.certificationirequest.UserAuthJpaRepository;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.userauth.converter.UserAuthConverter;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.port.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final UserAuthJpaRepository userAuthRepository;

    @Override
    public UserAuth save(UserAuth userAuth) {
        UserAuthEntity userAuthEntity = userAuthRepository.save(UserAuthConverter.toEntity(userAuth));
        return UserAuthConverter.toUserAuth(userAuthEntity);
    }

    @Override
    public UserAuth getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("UserAuths", id));
    }

    @Override
    public Optional<UserAuth> findById(long id) {
        return userAuthRepository.findById(id).map(UserAuthConverter::toUserAuth);
    }

    @Override
    public Page<UserAuth> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userAuthRepository.findAll(pageRequest).map(UserAuthConverter::toUserAuth);
    }

    @Override
    public void delete(UserAuth userAuth) {
        userAuthRepository.delete(UserAuthConverter.toEntity(userAuth));
    }
}
