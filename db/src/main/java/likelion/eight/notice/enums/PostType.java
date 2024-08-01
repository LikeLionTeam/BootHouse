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
    public static PostType from(String s) {
        for (PostType type : PostType.values()) {
            if (type.name().equalsIgnoreCase(s)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus: " + s);
    }
}
