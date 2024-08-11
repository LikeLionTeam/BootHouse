package likelion.eight.common.service.port;

import likelion.eight.common.domain.Log;

import java.util.Optional;

public interface LogRepository {

    Log save(Log log);

    Optional<Log> findByMethodName(String methodName);
}
