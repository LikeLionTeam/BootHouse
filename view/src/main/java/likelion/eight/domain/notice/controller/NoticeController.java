package likelion.eight.domain.notice.controller;

import likelion.eight.domain.notice.model.request.NoticeReq;
import likelion.eight.domain.notice.model.response.NoticeDetailRes;
import likelion.eight.domain.notice.model.response.NoticeRes;
import likelion.eight.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @PutMapping("/admin/notice/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable Long noticeId,
                                          @RequestBody NoticeReq noticeReq) {
        // todo: 작성자의 게시글인지 확인

        noticeService.updateNotice(noticeId, noticeReq);

        return ResponseEntity.ok("update success");
    }

    @DeleteMapping("/admin/notice/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeId) {
        // todo: 작성자의 게시글인지 확인

        noticeService.deleteNotice(noticeId);

        return ResponseEntity.ok("delete success");
    }

    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<?> getNoticeDetail(@PathVariable Long noticeId) {
        NoticeDetailRes noticeDetail = noticeService.getNoticeDetail(noticeId);

        return ResponseEntity.ok(noticeDetail);
    }

    @GetMapping("/notice")
    public ResponseEntity<?> getAllNotices() {
        List<NoticeRes> allNotices = noticeService.getAllNotices();

        return ResponseEntity.ok(allNotices);
    }

}
