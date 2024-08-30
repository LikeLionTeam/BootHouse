package likelion.eight.domain.userCourse.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCourse {
    private final Long id;
    private final Long userId;
    private final Long courseId;


    @Builder
    public UserCourse(Long id, Long userId, Long courseId) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
    }

}
