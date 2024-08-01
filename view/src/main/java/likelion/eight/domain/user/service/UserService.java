package likelion.eight.domain.user.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.common.service.port.UuidHolder;
import likelion.eight.domain.user.controller.model.UserCreateRequest;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UuidHolder uuidHolder;
    private final CertificationService certificationService;

    public User createUser(UserCreateRequest request){
        User createUser = UserConverter.toUser(request, uuidHolder);
        createUser = userRepository.save(createUser);
        certificationService.send(
                createUser.getEmail(),
                createUser.getId(),
                createUser.getCertificationCode()
        );
        return createUser;
    }

    public void verifyEmail(long id, String certificationCode){
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Users", id));
        user =  user.certificate(certificationCode);
        userRepository.save(user);
    }
}
