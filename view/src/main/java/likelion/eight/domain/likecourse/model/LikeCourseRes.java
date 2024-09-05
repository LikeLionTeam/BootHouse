package likelion.eight.domain.likecourse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class LikeCourseRes {
    private String name;
    private String summary;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean onlineOffline;
    private LocalDateTime closingDate;
}
