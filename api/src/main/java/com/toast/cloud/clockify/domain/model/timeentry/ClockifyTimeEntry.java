package com.toast.cloud.clockify.domain.model.timeentry;

import com.toast.cloud.clockify.infrastructure.service.timeentry.vo.TimeEntry;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ClockifyTimeEntry {

    private String id;
    private String description;
    private ZonedDateTime start;

    public ClockifyTimeEntry(TimeEntry timeEntry) {
        this.id = timeEntry.getId();
        this.description = timeEntry.getDescription();
        this.start = timeEntry.getTimeInterval().getStart().toZonedDateTime();
    }

}
