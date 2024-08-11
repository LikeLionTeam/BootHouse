package likelion.eight.common.domain;

import likelion.eight.log.LogEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Log {

    private final Long id;
    private final String methodName;
    private final long executionTime;

    @Builder
    public Log(Long id, long executionTime, String methodName) {
        this.id = id;
        this.executionTime = executionTime;
        this.methodName = methodName;
    }

    public static Log create(String methodName, long executionTime){
        return Log.builder()
                .methodName(methodName)
                .executionTime(executionTime)
                .build();
    }
}
