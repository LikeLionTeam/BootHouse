package likelion.eight.domain.user.service;

import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.certificationirequest.enums.AuthRequestType;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.UserAuthS3Service;
import likelion.eight.mock.*;
import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class UserAuthServiceTest {

    private UserAuthS3Service userAuthS3Service;

    @BeforeEach
    void init(){
        FakeUserAuthRepository userAuthRepository = new FakeUserAuthRepository();
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeS3Service s3Service = new FakeS3Service();
        ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 8, 8, 12, 0, 0));

        this.userAuthS3Service = UserAuthS3Service.builder()
                .userAuthRepository(userAuthRepository)
                .userRepository(userRepository)
                .s3ServiceImpl(s3Service)
                .clockHolder(clockHolder)
                .build();

        User user = userRepository.save(User.builder()
                .id(1L)
                .name("kim")
                .address("Seoul")
                .email("kin@anverc.om")
                .password("123")
                .phoneNumber("010-1111-1111")
                .certificationCode("1qw3")
                .userStatus(UserStatus.ACTIVE)
                .roleType(RoleType.USER)
                .image(null)
                .build());

        userAuthRepository.save(UserAuth.builder()
                .user(user)
                .authRequestType(AuthRequestType.BOOTCAMP)
                .authRequestStatus(AuthRequestStatus.PENDING)
                .build());
    }

    @Test
    void UserAuthCreateRequest를_이용하여_UserAuth를_만들수_있다(){
        //given(상황환경 세팅)
        MultipartFile multiPartFile = new TestMultiPartFile("image", "test.jpg", "image/jpeg", "Dummy Image Content".getBytes());

        UserAuthCreateRequest request = UserAuthCreateRequest.builder()
                .clientId(1L)
                .authRequestType(AuthRequestType.BOOTCAMP)
                .image(multiPartFile)
                .build();

        //when(상황발생)
        UserAuth userAuth = userAuthS3Service.create(request);

        //then(검증)
        assertThat(userAuth.getId()).isNotNull();
        assertThat(userAuth.getImageUrl()).isNotEmpty();
        assertThat(userAuth.getAuthRequestType()).isEqualTo(AuthRequestType.BOOTCAMP);
    }

    @Test
    void approve를_통해_UserAuthStatus를_APPROVAL으로_변경할_수_있다(){
        //given(상황환경 세팅)


        //when(상황발생)
        userAuthS3Service.approve(1L);

        //then(검증)
        UserAuth userAuth = userAuthS3Service.getById(1L);
        assertThat(userAuth.getAuthRequestStatus()).isEqualTo(AuthRequestStatus.APPROVAL);

    }
}
