package likelion.eight.domain.notice.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.notice.converter.MarkdownConverter;
import likelion.eight.domain.notice.model.request.NoticeReq;
import likelion.eight.domain.notice.model.response.NoticeDetailRes;
import likelion.eight.domain.notice.model.response.NoticeRes;
import likelion.eight.domain.notice.service.NoticeService;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.service.UserService;
import likelion.eight.notice.enums.PostType;
import likelion.eight.user.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final UserService userService;

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("notice", new NoticeReq());
        return "notice/noticeForm";
    }


    @PostMapping("/save")
    public String saveNotice(@ModelAttribute NoticeReq noticeReq,
                             @Login LoginUser loginUser){
        if(!loginUser.getRoleType().equals(RoleType.ADMIN)){
            return "redirect:/notice";
        }
        Long result = noticeService.saveNotice(noticeReq,loginUser);
        return "redirect:/notice/"+result;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String,Object>> uploadImage(@RequestParam("file") MultipartFile file){
        Map<String, Object> result = noticeService.uploadImage(file);

        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @ResponseBody
    @PutMapping("/{noticeId}")
    public ResponseEntity<?> updateNotice(@PathVariable Long noticeId,
                                          @RequestBody NoticeReq noticeReq,
                                          @Login LoginUser loginUser) {

        boolean result = noticeService.updateNotice(noticeId, noticeReq, loginUser);
        if(result){
            return ResponseEntity.ok(Map.of("redirectUrl", "/notice/" + noticeId));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unable to update notice"));
        }
    }

    @ResponseBody
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long noticeId,
                               @Login LoginUser loginUser) {

        boolean result = noticeService.deleteNotice(noticeId, loginUser);
        if(result){
            return ResponseEntity.ok(Map.of("redirectUrl", "/notice"));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unable to delete notice"));
        }
    }

    @GetMapping("/{noticeId}")
    public String getNoticeDetail(@PathVariable Long noticeId,Model model,
                                    @Login(required = false) LoginUser loginUser) {
        try {
            NoticeDetailRes noticeDetail = noticeService.getNoticeDetail(noticeId,loginUser);
            model.addAttribute("noticeDetail",noticeDetail);

            boolean isAdmin = loginUser != null && RoleType.ADMIN.equals(loginUser.getRoleType());
            model.addAttribute("isAdmin", isAdmin);

            return "notice/viewNotice";
        }catch (ResourceNotFoundException e){
            return "redirect:/notice";
        }
    }

    @GetMapping
    public String getAllNotices(@RequestParam(defaultValue = "0") int page,
                                Model model,
                                @Login(required = false) LoginUser loginUser
                                ) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("importance").descending().and(Sort.by("registrationDate").descending()));

        Page<NoticeRes> allNotices = noticeService.getAllNotices(pageable);
        boolean isAdmin = userService.isAdmin(loginUser);

        model.addAttribute("notices",allNotices);
        model.addAttribute("isAdmin", isAdmin);
//        model.addAttribute("isAdmin",loginUser.getRoleType().equals(RoleType.ADMIN));
        return "notice/viewAllNotice";
    }

}
