package likelion.eight.domain.course.model.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NaverMapRes {
    private String status;
    private Meta meta;
    private List<Address> addresses;
    private String errorMessage;
}
