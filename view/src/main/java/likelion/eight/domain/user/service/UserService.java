package likelion.eight.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.token.service.TokenCookieService;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.common.service.port.UuidHolder;
import likelion.eight.domain.user.controller.model.*;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.user.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UuidHolder uuidHolder;
    private final CertificationService certificationService;
    private final TokenCookieService tokenCookieService;
    private final ClockHolder clockHolder;


    public UserResponse createUser(UserCreateRequest request){
        User createUser = UserConverter.toUser(request, uuidHolder);
        createUser = userRepository.save(createUser);

        return UserConverter.toResponse(createUser);
    }

    public UserResponse createUser(UserCreateRequest request, String verificationCode) {

        User createUser = UserConverter.toUser(request, verificationCode);
        createUser = userRepository.save(createUser);

        return UserConverter.toResponse(createUser);
    }

    public String sendVerificationCode(String email) {
        // 인증 코드 생성
        String certificationCode = uuidHolder.random();

        // 인증 코드 이메일 전송
        certificationService.sendCode(email, certificationCode);

        return certificationCode;
    }

    public UserResponse getById(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
        return UserConverter.toResponse(user);
    }

    public UserResponse login(HttpServletResponse response, UserLoginRequest loginUser){

        User user = userRepository.findByEmail(loginUser.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("존재 하지 않는 이메일 입니다."));

        user = user.login(clockHolder, loginUser.getPassword());
        userRepository.save(user);

        if(Objects.equals(user.getRoleType(), RoleType.ADMIN)){
            tokenCookieService.createAdminCookie(response, user);
        }else{
            tokenCookieService.createUserCookie(response, user);
        }

        return UserConverter.toResponse(user);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        tokenCookieService.expiredCookie(request, response);
    }

    public void verifyEmail(long id, String certificationCode){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user =  user.certificate(certificationCode);
        userRepository.save(user);
    }

    public UserResponse editUser(Long id, UserEditRequest userEditRequest){
        User user = userRepository.getById(id);

        // 현재 비밀번호 확인
        if (!userEditRequest.getPassword().equals(user.getPassword())) {
            throw new ResourceNotFoundException("비밀번호가 일치하지 않습니다.");
        }
        user = user.edit(userEditRequest);
        userRepository.save(user);

        return UserConverter.toResponse(user);
    }

    public void findPassword(UserFindPasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("존재 하지 않는 이메일 입니다."));

        if(user.checkNameAndPhoneNumber(request)){
            User tempUser = user.issueTemporaryPassword(uuidHolder.random());
            userRepository.save(tempUser);
            certificationService.sendPassword(user.getEmail(), user.getPassword());
        }else{
            throw new ResourceNotFoundException("입력하신 핸드폰 번호, 이름 정보가 일치 하지 않습니다");
        }
    }

    public UserResponse findEmail(UserFindEmailRequest request){
        User user = userRepository.findByPhoneAndName(request.getPhoneNumber(), request.getName())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 정보와 일치하는 이메일이 존재 하지 않습니다."));

        return UserConverter.toResponse(user);
    }

    public boolean isAdmin (LoginUser loginuser) {
        if (loginuser == null) {
            return false;
        }
        return RoleType.ADMIN.equals(loginuser.getRoleType());
    }

//    ---

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
