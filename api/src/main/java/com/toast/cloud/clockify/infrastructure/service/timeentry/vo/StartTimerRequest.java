package com.toast.cloud.clockify.infrastructure.service.timeentry.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class StartTimerRequest {

    private final ZonedDateTime start;
    private final String billable = "true";
    private final String description;
    private final String projectId;
    private final String taskId = null;
    private final String end = null;
    private final List<String> tagIds = List.of("5d5ccf6f3e86250cf9f4f62e");

}
