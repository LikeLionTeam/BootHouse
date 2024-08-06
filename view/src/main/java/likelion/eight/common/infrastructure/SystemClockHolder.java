package likelion.eight.common.infrastructure;

import likelion.eight.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

    @Override
    public LocalDateTime plusHours(Long hours) {
        return LocalDateTime.now().plusHours(hours);
    }

    @Override
    public Date convertAbsoluteTime(LocalDateTime expiredTime) {
        return Date.from(expiredTime.atZone(
                        ZoneId.systemDefault())
                .toInstant());
    }
}
