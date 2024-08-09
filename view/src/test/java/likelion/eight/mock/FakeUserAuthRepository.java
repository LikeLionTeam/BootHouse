package likelion.eight.mock;

import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.port.UserAuthRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserAuthRepository implements UserAuthRepository {

    private final AtomicLong id = new AtomicLong(0);
    private final List<UserAuth> data = new ArrayList<>();


    @Override
    public UserAuth save(UserAuth userAuth) {
        if(userAuth.getId() == null || userAuth.getId() == 0)
        {
            UserAuth newUserAuth = UserAuth.builder()
                    .id(id.incrementAndGet())
                    .user(userAuth.getUser())
                    .authRequestType(userAuth.getAuthRequestType())
                    .authRequestStatus(userAuth.getAuthRequestStatus())
                    .determined_at(userAuth.getDetermined_at())
                    .image(userAuth.getImage())
                    .imageUrl(userAuth.getImageUrl())
                    .build();
            data.add(newUserAuth);
            return  newUserAuth;
        }else{
            data.removeIf(u -> Objects.equals(u.getId(), userAuth.getId()));
            data.add(userAuth);
            return userAuth;
        }
    }

    @Override
    public UserAuth getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public Optional<UserAuth> findById(long id) {
        return data.stream().filter(u -> u.getId().equals(id)).findAny();
    }

    @Override
    public Page<UserAuth> findAll(int page, int size) { //TODO 공부필요
        // 페이지 요청을 생성합니다.
        PageRequest pageRequest = PageRequest.of(page, size);

        // 전체 데이터에서 페이지에 해당하는 부분을 추출합니다.
        int start = Math.toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), data.size());

        // 시작 인덱스가 데이터 사이즈보다 크면 빈 리스트를 반환합니다.
        if (start > data.size()) {
            return new PageImpl<>(Collections.emptyList(), pageRequest, data.size());
        }

        // 페이지에 해당하는 서브리스트를 추출합니다.
        List<UserAuth> subList = data.subList(start, end);

        // PageImpl 객체를 생성하여 반환합니다.
        return new PageImpl<>(subList, pageRequest, data.size());
    }

    @Override
    public void delete(UserAuth userAuth) {
        data.remove(userAuth);
    }
}
