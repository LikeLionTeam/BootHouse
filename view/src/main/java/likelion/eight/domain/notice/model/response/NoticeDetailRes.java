package likelion.eight.domain.notice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoticeDetailRes {
    private String title;
    private String content;
    private String postType;
    private Boolean importance;
}
