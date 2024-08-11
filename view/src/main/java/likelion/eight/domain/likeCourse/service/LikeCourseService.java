package likelion.eight.domain.likeCourse.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.likeCourse.model.LikeCourseRes;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.likeCourse.LikeCourseEntity;
import likelion.eight.likeCourse.LikeCourseJpaRepository;
import likelion.eight.user.UserEntity;
import likelion.eight.user.ifs.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeCourseService {
    private final LikeCourseJpaRepository likeCourseJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Page<LikeCourseRes> getAllCourseLikes(LoginUser loginUser, Pageable pageable){
        UserEntity user = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지않는 사용자"));
        Page<LikeCourseEntity> allCourseLikes = likeCourseJpaRepository.findAllByUserEntity(user,pageable);

        return allCourseLikes.map(likes -> new LikeCourseRes(
                likes.getCourseEntity().getName(),
                likes.getCourseEntity().getSummary(),
                likes.getCourseEntity().getStartDate(),
                likes.getCourseEntity().getEndDate(),
                likes.getCourseEntity().isOnlineOffline(),
                likes.getCourseEntity().getClosingDate()
        ));
    }
}
