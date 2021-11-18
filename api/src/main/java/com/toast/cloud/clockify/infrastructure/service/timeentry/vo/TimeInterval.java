package com.toast.cloud.clockify.infrastructure.service.timeentry.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeInterval {

    private OffsetDateTime start;

}
