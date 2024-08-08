package likelion.eight.mock;

import likelion.eight.common.service.port.ClockHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FakeClockHolder implements ClockHolder {

    long mills;
    LocalDateTime localDateTime; // LocalDateTime.of(2023, 11, 10, 13, 30, 0);

    public FakeClockHolder(long mills) {
        this.mills = mills;
    }

    public FakeClockHolder(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public long millis() {
        return mills;
    }

    @Override
    public LocalDateTime now() {
        return localDateTime;
    }

    @Override
    public LocalDateTime plusHours(Long hours) {
        return localDateTime.plusHours(hours);
    }

    @Override
    public Date convertAbsoluteTime(LocalDateTime expiredTime) {
        return Date.from(expiredTime.atZone(
                        ZoneId.systemDefault())
                .toInstant());
    }
}
