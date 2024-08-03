package likelion.eight.common.infrastructure;

import likelion.eight.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
