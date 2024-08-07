package likelion.eight.domain.notice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoticeRes {
    private Long noticeId;
    private String title;
    private String postType;
    private Boolean importance;
}
