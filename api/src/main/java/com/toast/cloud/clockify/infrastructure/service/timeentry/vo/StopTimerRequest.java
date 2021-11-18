package com.toast.cloud.clockify.infrastructure.service.timeentry.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Getter
public class StopTimerRequest {

    private final ZonedDateTime end;

}
