package likelion.eight.domain.user.model;

import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.certificationirequest.enums.AuthRequestType;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.converter.UserAuthConverter;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.mock.FakeS3Service;
import likelion.eight.mock.FakeUserRepository;
import likelion.eight.mock.FakeClockHolder;
import likelion.eight.mock.TestMultiPartFile;
import likelion.eight.user.enums.RoleType;
import likelion.eight.user.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class UserAuthTest {

    private FakeUserRepository userRepository;
    private MultipartFile multiPartFile;
    private FakeS3Service s3Service;

    @BeforeEach
    void init(){
        userRepository = new FakeUserRepository();
        s3Service = new FakeS3Service();
        multiPartFile = new TestMultiPartFile("image", "test.jpg", "image/jpeg", "Dummy Image Content".getBytes());
        userRepository.save(User.builder()
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
    }


    @Test
    void UserAuthCreateRequest로_UserAuth를_만들수_있다() throws IOException {
        //given(상황환경 세팅)
        UserAuthCreateRequest request = UserAuthCreateRequest.builder()
                .clientId(1L)
                .authRequestType(AuthRequestType.BOOTCAMP)
                .image(multiPartFile)
                .build();

        //when(상황발생)
        User user = userRepository.getById(request.getClientId());
        String imageUrl = s3Service.saveFile(request.getImage());
        UserAuth userAuth = UserAuthConverter.toUserAuth(user, request, imageUrl);

        //then(검증)
        assertThat(userAuth.getUser()).isEqualTo(user);
        assertThat(userAuth.getAuthRequestType()).isEqualTo(request.getAuthRequestType());
        assertThat(userAuth.getImageUrl()).isEqualTo(imageUrl);
    }

    @Test
    void 거절을_하면_상태가_DENY로_변경되고_결정시간이_정해진다(){

        User user = userRepository.getById(1L);
        UserAuth userAuth = UserAuth.builder()
                .user(user)
                .authRequestType(AuthRequestType.BOOTCAMP)
                .authRequestStatus(AuthRequestStatus.PENDING)
                .build();
        ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 8, 8, 12, 0, 0));

        //When
        UserAuth denyUser = userAuth.deny(clockHolder);

        //then
        assertThat(denyUser.getAuthRequestStatus()).isEqualTo(AuthRequestStatus.DENY);
        assertThat(denyUser.getDetermined_at()).isEqualTo(LocalDateTime.of(2024, 8, 8, 12, 0, 0));
    }
}
