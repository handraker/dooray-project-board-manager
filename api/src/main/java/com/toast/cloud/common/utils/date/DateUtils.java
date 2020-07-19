package com.toast.cloud.common.utils.date;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {

    public static int getWokringDays(final LocalDate fromInclusive, final LocalDate toInclusive) {
        int workingDays = 0;
        LocalDate at = fromInclusive;
        while (!at.isAfter(toInclusive)) {
            if (at.getDayOfWeek() != DayOfWeek.SATURDAY && at.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays += 1;
            }

            at = at.plusDays(1);
        }
        return workingDays;
    }

}
