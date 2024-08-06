package likelion.eight.domain.course.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseFilter {
    private String onlineOffline;
    private String location;
    private String cost;
    private String participationTime;
    private String selectionProcedure;
    private Long subCourse;
}
