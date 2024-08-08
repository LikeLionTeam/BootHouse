package likelion.eight.mock;

import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.common.service.port.S3Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class FakeS3Service implements S3Service {
    private Map<String, byte[]> storage = new HashMap<>();
    private String bucket = "fake-bucket";

    @Override
    public List<String> saveFiles(List<MultipartFile> multipartFiles) {
        return null;
    }

    @Override
    public String saveFile(MultipartFile file) {
        String randomFileName = generateRandomFileName(file);
        try {
            storage.put(randomFileName, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
        return "http://localhost/" + bucket + "/" + randomFileName;
    }

    @Override
    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        storage.remove(fileName);
    }

    private String generateRandomFileName(MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = validateFileExtension(originalFilename);
        String randomFileName = UUID.randomUUID() + "." + fileExtension;
        return randomFileName;
    }

    private String validateFileExtension(String originFileName){
        String fileExtension = originFileName.substring(originFileName.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "png", "jpeg");

        if(!allowedExtensions.contains(fileExtension)){
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 400error
        }

        return fileExtension;
    }
}
