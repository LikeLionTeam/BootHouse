package likelion.eight.domain.notice.controller;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.notice.model.request.NoticeReq;
import likelion.eight.domain.notice.model.response.NoticeDetailRes;
import likelion.eight.domain.notice.model.response.NoticeRes;
import likelion.eight.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("notice", new NoticeReq());
        return "/notice/noticeForm";
    }


    @PostMapping("/notice/save")
    public String saveNotice(@ModelAttribute NoticeReq noticeReq){
        Long result = noticeService.saveNotice(noticeReq);
        return "redirect:/notice/"+result;
    }

    @PostMapping("/notice/upload")
    public ResponseEntity<Map<String,Object>> uploadImage(@RequestParam("file") MultipartFile file){
        Map<String, Object> result = noticeService.uploadImage(file);

        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PutMapping("/notice/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable Long noticeId,
                                          @RequestBody NoticeReq noticeReq) {
        // todo: 작성자의 게시글인지 확인

        noticeService.updateNotice(noticeId, noticeReq);

        return ResponseEntity.ok("update success");
    }

    @DeleteMapping("/notice/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeId) {
        // todo: 작성자의 게시글인지 확인

        noticeService.deleteNotice(noticeId);

        return ResponseEntity.ok("delete success");
    }

    @GetMapping("/notice/{noticeId}")
    public String getNoticeDetail(@PathVariable Long noticeId,Model model) {
        try {
            NoticeDetailRes noticeDetail = noticeService.getNoticeDetail(noticeId);
            model.addAttribute("noticeDetail",noticeDetail);
            return "/notice/viewNotice";
        }catch (ResourceNotFoundException e){
            return "redirect:/notice";
        }
    }

    @GetMapping("/notice")
    public String getAllNotices(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<NoticeRes> allNotices = noticeService.getAllNotices(pageable);
        model.addAttribute("notices",allNotices);
        return "/notice/viewAllNotice";
    }

}
