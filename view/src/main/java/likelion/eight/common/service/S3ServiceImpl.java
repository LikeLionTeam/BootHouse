package likelion.eight.common.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.common.service.port.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;
    private Set<String> uploadFileNames = new HashSet<>();
    private Set<Long> uploadedFilesSizes = new HashSet<>();

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSizeString;

    //여러장의 파일 저장
    public List<String> saveFiles(List<MultipartFile> multipartFiles){
        List<String> uploadUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            if(isDuplicate(multipartFile)){
                throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 400error
            }

            String uploadedUrl = saveFile(multipartFile);
            uploadUrls.add(uploadedUrl);
        }

        clear();
        return uploadUrls;
    }

    public String saveFile(MultipartFile file){
        String randomFileName = generateRandomFileName(file);

        log.info("File upload started: " + randomFileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try{
            amazonS3.putObject(bucket, randomFileName, file.getInputStream(), metadata);
        }catch(AmazonS3Exception e){
            log.error("Amazon S3 error while uploading file: " + e.getMessage());
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 500error
        }catch (SdkClientException e){
            log.error("AWS SDK client error while uploading file: " + e.getMessage());
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 500error
        }catch (IOException e){
            log.error("IO error while uploading file: " + e.getMessage());
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 500error
        }

        log.info("File upload completed: " + randomFileName);

        return amazonS3.getUrl(bucket, randomFileName).toString();
    }

    public void deleteFile(String fileUrl){
        String[] urlParts = fileUrl.split("/");
        String fileBucket = urlParts[2].split("\\.")[0];

        if(!fileBucket.equals(bucket)){
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 400error
        }

        String objectKey = String.join("/", Arrays.copyOfRange(urlParts, 3, urlParts.length));

        if(!amazonS3.doesObjectExist(bucket, objectKey)){
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 400error
        }

        try{
            amazonS3.deleteObject(bucket, objectKey);
        }catch(AmazonS3Exception e){
            log.error("File delete fail : " + e.getMessage());
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 500error
        }catch (SdkClientException e) {
            log.error("AWS SDK client error : " + e.getMessage());
            throw new CertificationFailedException("추후 전용 예외 생성"); //TODO 500error
        }

        log.info("File delete complete: " + objectKey);
    }

    private boolean isDuplicate(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        Long fileSize = multipartFile.getSize();

        if(uploadFileNames.contains(fileName) && uploadedFilesSizes.contains(fileSize)){
            return true;
        }

        uploadFileNames.add(fileName);
        uploadedFilesSizes.add(fileSize);

        return false;
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

    private void clear(){
        uploadedFilesSizes.clear();
        uploadFileNames.clear();
    }
}
