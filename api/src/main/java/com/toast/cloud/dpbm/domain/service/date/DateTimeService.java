package com.toast.cloud.dpbm.domain.service.date;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

@Service
public class DateTimeService {

    public int getWokringDays(final ZonedDateTime fromInclusive, final ZonedDateTime toInclusive) {
        int workingDays = 0;
        ZonedDateTime at = fromInclusive;
        while (at.isBefore(toInclusive)) {
            if (at.getDayOfWeek() != DayOfWeek.SATURDAY && at.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays += 1;
            }

            at = at.plusDays(1);
        }
        return workingDays;
    }

}
