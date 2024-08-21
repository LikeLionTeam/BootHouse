package likelion.eight.common.index.controller.model;

import likelion.eight.domain.course.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String summary;
    private LocalDate startDate;
    private String tuitionType;
    private String bootcampName;

    public CourseDto(Course course){
        this.id = course.getId();
        this.name = course.getName();
        this.summary = course.getSummary();
        this.startDate = course.getStartDate();
        this.tuitionType = course.getTuitionType();
        this.bootcampName = course.getBootcamp().getName();
    }
}
