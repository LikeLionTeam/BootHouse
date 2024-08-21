package likelion.eight.domain.course.model.map;

import lombok.Data;

import java.util.List;

@Data
public class AddressElement {
    private List<String> types;
    private String longName;
    private String shortName;
    private String code;
}
