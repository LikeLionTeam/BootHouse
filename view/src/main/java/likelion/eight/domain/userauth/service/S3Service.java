package likelion.eight.domain.userauth.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {

    List<String> saveFiles(List<MultipartFile> multipartFiles);

    String saveFile(MultipartFile file);

    void deleteFile(String fileUrl);
}
