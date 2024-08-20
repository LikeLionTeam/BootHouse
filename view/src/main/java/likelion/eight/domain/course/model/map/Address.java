package likelion.eight.domain.course.model.map;

import lombok.Data;

import java.util.List;

@Data
public class Address {
    private String roadAddress;
    private String jibunAddress;
    private String englishAddress;
    private List<AddressElement> addressElements;
    private String x;
    private String y;
    private double distance;
}
