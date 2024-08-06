package likelion.eight.certificationirequest.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuthRequestStatus {

    PENDING("대기"),
    APPROVAL("승인"),
    DENY("미승인")
    ;

    private String description;

    @JsonCreator
    public static AuthRequestStatus from(String s) {
        for (AuthRequestStatus status : AuthRequestStatus.values()) {
            if (status.name().equalsIgnoreCase(s)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus: " + s);
    }
}
