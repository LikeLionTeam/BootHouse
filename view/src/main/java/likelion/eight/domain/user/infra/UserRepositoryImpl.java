package likelion.eight.domain.user.infra;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.user.UserEntity;
import likelion.eight.user.ifs.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userRepository;

    @Override
    public User save(User user){
        UserEntity userEntity = userRepository.save(UserConverter.toEntity(user));
        return UserConverter.toUser(userEntity);
    }

    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Users", id)
        );
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id).map(UserConverter::toUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserConverter::toUser);
    }

    @Override
    public Optional<User> findByPhoneAndName(String phoneNumber, String name) {
        return userRepository.findByPhoneNumberAndName(phoneNumber, name).map(UserConverter::toUser);
    }

//    ---

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(UserConverter::toUser)
                .collect(Collectors.toList());
    }

}
