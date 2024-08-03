package likelion.eight.domain.course.model;

import lombok.Getter;

@Getter
public enum RecruitmentStatus {
    OPEN("open", "모집중"),
    CLOSED("closed", "모집마감");

    private String displayName;
    private String description;

    RecruitmentStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
