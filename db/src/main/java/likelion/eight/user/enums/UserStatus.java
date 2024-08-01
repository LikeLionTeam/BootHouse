package likelion.eight.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    PENDING("대기"),
    ACTIVE("활성"),
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
