package likelion.eight.certificationirequest.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuthRequestType {

    BOOTCAMP("부트캠프인증요청"),
    COMPANY("회사인증요청"),
    ;

    private String description;

    @JsonCreator
    public static AuthRequestType from(String s) {
        for (AuthRequestType status : AuthRequestType.values()) {
            if (status.name().equalsIgnoreCase(s)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus: " + s);
    }
}
