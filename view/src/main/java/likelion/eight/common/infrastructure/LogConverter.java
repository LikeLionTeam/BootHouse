package likelion.eight.common.infrastructure;

import likelion.eight.common.domain.Log;
import likelion.eight.log.LogEntity;

public class LogConverter {

    public static LogEntity toEntity(Log log){
        return LogEntity.builder()
                .methodName(log.getMethodName())
                .executionTime(log.getExecutionTime())
                .build();
    }

    public static Log toLog(LogEntity logEntity){
        return Log.builder()
                .id(logEntity.getId())
                .methodName(logEntity.getMethodName())
                .executionTime(logEntity.getExecutionTime())
                .build();
    }
}
