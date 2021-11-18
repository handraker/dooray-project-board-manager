package com.toast.cloud.clockify.infrastructure.service.timeentry.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEntry {

    private String id;
    private String description;
    private TimeInterval timeInterval;

}
