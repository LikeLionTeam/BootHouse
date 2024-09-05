package likelion.eight.log;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter
@Table(name = "logs")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class LogEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    private long executionTime;
    private String methodName;

    public LogEntity(String methodName, long executionTime) {
        this.methodName = methodName;
        this.executionTime = executionTime;
    }
}
