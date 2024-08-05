package likelion.eight.common.service.port;

import java.time.LocalDateTime;

public interface ClockHolder {

    long millis();
    LocalDateTime now();
}
