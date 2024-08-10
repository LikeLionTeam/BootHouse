package likelion.eight.common.service;

import likelion.eight.common.domain.Log;
import likelion.eight.common.service.port.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LogService {

    private final LogRepository logRepository;

    public void saveTxLog(String methodName, long executionTime){
        if(executionTime > 99L){
            Log log = Log.create(methodName, executionTime);
            logRepository.save(log);
        }
    }

}
