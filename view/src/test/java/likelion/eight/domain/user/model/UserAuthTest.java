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
    @Test
    void UserAuthCreateRequest로_UserAuth를_만들수_있다() throws IOException {
        //given(상황환경 세팅)
        User user = User.builder()
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
                .build();

        MultipartFile multiPartFile = new TestMultiPartFile("image", "test.jpg", "image/jpeg", "Dummy Image Content".getBytes());
        String imageUrl = "www.test.com";

        UserAuthCreateRequest request = UserAuthCreateRequest.builder()
                .clientId(1L)
                .authRequestType(AuthRequestType.BOOTCAMP)
                .image(multiPartFile)
                .build();

        //when(상황발생)
        UserAuth userAuth = UserAuthConverter.toUserAuth(user, request, imageUrl);

        //then(검증)
        assertThat(userAuth.getUser()).isEqualTo(user);
        assertThat(userAuth.getAuthRequestType()).isEqualTo(request.getAuthRequestType());
        assertThat(userAuth.getImageUrl()).isEqualTo(imageUrl);
    }

    @Test
    void 거절을_하면_상태가_DENY로_변경되고_결정시간이_정해진다(){

        //given(상황환경 세팅)
        User user = User.builder()
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
                .build();

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
