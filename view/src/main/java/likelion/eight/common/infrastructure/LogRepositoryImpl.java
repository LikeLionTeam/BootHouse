package likelion.eight.common.infrastructure;

import likelion.eight.common.domain.Log;
import likelion.eight.common.service.port.LogRepository;
import likelion.eight.log.LogEntity;
import likelion.eight.log.LogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LogRepositoryImpl implements LogRepository {

    private final LogJpaRepository logRepository;

    @Override
    public Log save(Log log) {
        LogEntity logEntity = logRepository.save(LogConverter.toEntity(log));
        return LogConverter.toLog(logEntity);
    }

    @Override
    public Optional<Log> findByMethodName(String methodName) {
        return logRepository.findByMethodName(methodName).map(LogConverter::toLog);
    }
}
