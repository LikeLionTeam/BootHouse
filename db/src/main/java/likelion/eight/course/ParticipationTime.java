package likelion.eight.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
    참여유형 : FULL_TIME, PART_TIME
    풀타임은 고정 (월~금, 9시 ~ 18시)
    파트타임은 그때마다 다르므로, null로 초기화
 */
@Getter
public enum ParticipationTime {

    FULL_TIME("Full Time", "월~금, 09:00 ~ 18:00"),
    PART_TIME("Part Time", "");

    private String displayName;
    private String defaultSchedule;

    ParticipationTime(String displayName, String defaultSchedule) {
        this.displayName = displayName;
        this.defaultSchedule = defaultSchedule;
    }

    // 파트타임의 경우 상세 일정을 설정할 수 있도록 setter 추가
    public void setDefaultSchedule(String schedule) {
        if (this == PART_TIME) {
            this.defaultSchedule = schedule;
        }
    }
}
