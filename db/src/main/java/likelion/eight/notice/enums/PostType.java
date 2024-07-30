package likelion.eight.notice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import likelion.eight.user.enums.GenderType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PostType {

    NOTICE("공지사항"),
    EVENT("이벤트"),
    ;

    private String description;

    @JsonCreator
    public static GenderType from(String s) {
        for (GenderType status : GenderType.values()) {
            if (status.name().equalsIgnoreCase(s)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus: " + s);
    }
}
