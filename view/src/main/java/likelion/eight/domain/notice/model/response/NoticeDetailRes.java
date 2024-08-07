package likelion.eight.domain.notice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NoticeDetailRes {
    private Long noticeId;
    private String author;
    private String title;
    private String content;
    private String postType;
    private Boolean importance;
    private Boolean isOwner;
}
