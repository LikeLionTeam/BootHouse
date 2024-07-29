package likelion.eight.profile.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderType {
    MALE("남성"),
    FEMALE("여성"),
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
