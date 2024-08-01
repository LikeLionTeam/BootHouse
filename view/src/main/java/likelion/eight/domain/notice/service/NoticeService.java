package likelion.eight.domain.notice.service;

import likelion.eight.domain.notice.converter.MarkdownConverter;
import likelion.eight.domain.notice.model.NoticeReq;
import likelion.eight.notice.NoticeEntity;
import likelion.eight.notice.NoticeJpaRepository;
import likelion.eight.notice.enums.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeJpaRepository noticeJpaRepository;
    private final MarkdownConverter markdownConverter;

    @Value("${app.upload.url}")
    private String UPLOAD_DIR;

    public Long saveNotice(NoticeReq noticeReq){
        String htmlContent = markdownConverter.convertMarkdownToHtml(noticeReq.getContent());

        NoticeEntity notice = NoticeEntity.builder()
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
                result.put("error", "파일 업로드 실패" + e.getMessage());
                return result;
            }
        }else {
            result.put("error","파일 없음");
            return result;
        }
    }

    private String storeFile(MultipartFile file){
        try {
            String fileName = UUID.randomUUID() + ".jpg";
            Path targetLocation = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize().resolve(fileName);

            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/images/" + fileName;
        }catch (IOException e){
            throw new RuntimeException("이미지 저장 오류");
        }
    }

}
