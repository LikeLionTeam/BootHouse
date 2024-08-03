package likelion.eight.domain.userauth.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.converter.UserAuthConverter;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.port.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public UserAuth create(UserAuthCreateRequest request){
        User client = userRepository.getById(request.getClientId());
        try{
            UserAuth userAuth = UserAuthConverter.toUserAuth(client, request);
            userAuthRepository.save(userAuth);
            return userAuth;
        }catch (IOException e){
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public byte[] getUserAuthImage(long id){
        UserAuth userAuth = userAuthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAuth", id));

        return Optional.ofNullable(userAuth.getImage())
                .map(it -> {return userAuth.getImage();})
                .orElseThrow(() -> new ResourceNotFoundException("AuthImage", id));
    }

    @Transactional(readOnly = true)
    public Page<UserAuth> getUserAuthPage(int page, int size){
        return userAuthRepository.findAll(page, size);
    }

    public void approval(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.approve(clockHolder);
        userAuthRepository.save(userAuth);
    }

    public void deny(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.deny(clockHolder);
        userAuthRepository.save(userAuth);
    }
}
