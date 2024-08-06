package likelion.eight.domain.notice.service;


import jakarta.validation.constraints.NotNull;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.notice.converter.MarkdownConverter;
import likelion.eight.domain.notice.model.request.NoticeReq;
import likelion.eight.domain.notice.model.response.NoticeDetailRes;
import likelion.eight.domain.notice.model.response.NoticeRes;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.notice.NoticeEntity;
import likelion.eight.notice.NoticeJpaRepository;
import likelion.eight.notice.enums.PostType;
import likelion.eight.user.UserEntity;
import likelion.eight.user.ifs.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeJpaRepository noticeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final MarkdownConverter markdownConverter;

    @Value("${app.upload.url}")
    private String UPLOAD_DIR;

    public Long saveNotice(NoticeReq noticeReq, LoginUser loginUser){
        String htmlContent = markdownConverter.convertMarkdownToHtml(noticeReq.getContent());


        UserEntity userEntity = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지않는 user"));


        NoticeEntity notice = NoticeEntity.builder()
                .userEntity(userEntity)
                .postType(PostType.valueOf(noticeReq.getPostType()))
                .title(noticeReq.getTitle())
                .content(htmlContent)
                .importance(noticeReq.getImportance())
                .build();
        NoticeEntity savedNotice = noticeJpaRepository.save(notice);

        return savedNotice.getId();
    }

    public Map<String,Object> uploadImage(MultipartFile file){
        Map<String,Object> result = new HashMap<>();
        if(file != null && !file.isEmpty()){
            try {
                String imageUrl = storeFile(file);
                result.put("url",imageUrl);
                return result;
            }catch (Exception e){
                log.info(e.getMessage());
                result.put("error", "파일 업로드 실패" + e.getMessage());
                return result;
            }
        }else {
            result.put("error","파일 없음");
            return result;
        }
    }

    public boolean updateNotice(Long noticeId, NoticeReq noticeReq,LoginUser loginUser) {

        UserEntity userEntity = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지않는 user"));
        NoticeEntity notice = getNotice(noticeId);

        if(!notice.getUserEntity().getId().equals(userEntity.getId())){
            return false;
        }else {

            String content = markdownConverter.convertMarkdownToHtml(noticeReq.getContent());

            notice.setTitle(noticeReq.getTitle());
            notice.setContent(content);
            notice.setPostType(PostType.valueOf(noticeReq.getPostType()));
            notice.setImportance(noticeReq.getImportance());
            noticeJpaRepository.save(notice);
            return true;
        }
    }



    public boolean deleteNotice(Long noticeId,LoginUser loginUser) {
        NoticeEntity notice = noticeJpaRepository.findById(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지않는 notice"));

        if(notice.getUserEntity().getId().equals(loginUser.getId())){
            noticeJpaRepository.deleteById(noticeId);
            return true;
        }
        return false;
    }

    public NoticeDetailRes getNoticeDetail(Long noticeId,LoginUser loginUser) {
        NoticeEntity notice = getNotice(noticeId);

        NoticeDetailRes detailNotice = NoticeDetailRes.builder()
                .noticeId(notice.getId())
                .author(notice.getUserEntity().getName())
                .title(notice.getTitle())
                .content(notice.getContent())
                .postType(notice.getPostType().toString())
                .importance(notice.getImportance())
                .isOwner(false)
                .build();

        if(loginUser!=null&&notice.getUserEntity().getId().equals(loginUser.getId())){
            detailNotice.setIsOwner(true);
        }
        log.info("isOwner: "+detailNotice.getIsOwner());

        return detailNotice;
    }

    public Page<NoticeRes> getAllNotices(Pageable pageable) {
        Page<NoticeEntity> allNotices = noticeJpaRepository.findAll(pageable);

        return allNotices.map(notice -> NoticeRes.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .postType(notice.getPostType().toString())
                .importance(notice.getImportance())
                .build());
    }

    @NotNull
    private NoticeEntity getNotice(Long noticeId) {
        Optional<NoticeEntity> noticeOptional = noticeJpaRepository.findById(noticeId);

        if (noticeOptional.isEmpty()) {
            throw new ResourceNotFoundException("notice", noticeId);
        }

        return noticeOptional.get();
    }

    public String storeFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + ".jpg";
            Path targetLocation = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize().resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/noticeImages/" + fileName;  // 웹에서 접근 가능한 URL 형태로 변경
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }
}
