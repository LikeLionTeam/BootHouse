package likelion.eight.domain.notice.controller;

import likelion.eight.domain.notice.model.NoticeReq;
import likelion.eight.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/admin/notice/save")
    public Long saveNotice(@RequestBody NoticeReq noticeReq){
         return noticeService.saveNotice(noticeReq);
    }

    @PostMapping("/admin/notice/upload")
    public ResponseEntity<Map<String,Object>> uploadImage(@RequestParam("file")MultipartFile file){
        Map<String, Object> result = noticeService.uploadImage(file);

        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

}
