package likelion.eight.domain.bootcamp.service;

import likelion.eight.common.domain.exception.FileStorageException;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.bootcamp.controller.model.BootcampCreateRequest;
import likelion.eight.domain.bootcamp.model.Bootcamp;
import likelion.eight.domain.bootcamp.service.port.BootcampRepository;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BootcampService {
    private final BootcampRepository bootcampRepository;
    private final CourseRepository courseRepository;


    @Value("${app.upload.url}")
    private String fileDir;

    public List<Bootcamp> findAllBootcamps(){
        List<Bootcamp> bootcamps = bootcampRepository.findAllBootcamps();

        if (bootcamps.isEmpty()){
            throw new ResourceNotFoundException("현재 등록된 부트캠프가 없습니다.");
        }
        return bootcamps;
    }

    public List<Bootcamp> findAllBootcamps(String searchKeyword){
        List<Bootcamp> bootcamps = bootcampRepository.findByName(searchKeyword);
        return bootcamps;
    }

//     public List<BootCampSearchResponse> findAllBootcamps(BootCampSearchCond cond){
//         List<BootCampSearchResponse> bootcamps = bootcampRepository.findSearchByCond(cond);
//         return bootcamps;
//     }

    public Bootcamp createBootcamp(BootcampCreateRequest request){
        String name = request.getName();
        String description = request.getDescription();
        String location = request.getLocation();
        String url = request.getUrl();
        MultipartFile file = request.getFile();

        // 파일 처리 먼저
        String logoFilename = null;
        if (file != null && !file.isEmpty()){
            logoFilename = saveFile(file);
        }

        // Bootcamp 저장
        Bootcamp bootcamp = Bootcamp.builder()
                .name(name)
                .description(description)
                .location(location)
                .url(url)
                .logo(logoFilename) // UUID 파일명만 저장
                .build();

        return bootcampRepository.save(bootcamp);
    }

    // 파일 저장
    public String saveFile(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String saveFilename = UUID.randomUUID().toString() + "." + extractExt(originalFilename);

        try {
            file.transferTo(new File(fileDir + saveFilename));
        } catch (IOException e) {
            throw new FileStorageException("파일 저장 실패 : " + originalFilename);
        }
        return saveFilename; // UUID 파일명
    }

    private String extractExt(String originalFilename){
        int idx = originalFilename.lastIndexOf(".");
        return originalFilename.substring(idx + 1);
    }

    public String getFilePath(String saveFilename){
        return fileDir + saveFilename;
    }

    public List<Course> findCourseByBootcampId(Long bootcampId){
        List<Course> courses = courseRepository.findCourseByBootcampId(bootcampId);

        return Optional.ofNullable(courses)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("해당 부트캠프에서 주관하는 course는 없습니다."));
    }
}
