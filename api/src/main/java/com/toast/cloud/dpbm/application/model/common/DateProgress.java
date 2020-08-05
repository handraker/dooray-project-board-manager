package com.toast.cloud.dpbm.application.model.common;

import com.toast.cloud.common.utils.date.DateUtils;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateProgress {

    private LocalDate from;
    private LocalDate to;
    private int totalWorkingDays;
    private int remainingWorkingDays;

    public DateProgress() {
        this.from = null;
        this.to = null;
        this.totalWorkingDays = 0;
        this.remainingWorkingDays = 0;
    }

    public DateProgress(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
        if (from != null && to != null) {
            this.totalWorkingDays = DateUtils.getWokringDays(from, to);
            this.remainingWorkingDays = DateUtils.getWokringDays(LocalDate.now(), to);
        }
    }

}
