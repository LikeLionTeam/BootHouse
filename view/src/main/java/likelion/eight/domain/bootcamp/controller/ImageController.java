package likelion.eight.domain.bootcamp.controller;

import likelion.eight.common.domain.exception.FileStorageException;
import likelion.eight.domain.bootcamp.service.BootcampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final BootcampService bootcampService;
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource loadImage(@PathVariable String filename){
        try {
            return new UrlResource("file:" + bootcampService.getFilePath(filename));
        } catch (MalformedURLException e) {
            throw new FileStorageException("파일 로드 실패 : " + e.getMessage());
        }
    }
}
