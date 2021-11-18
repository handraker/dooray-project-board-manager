package com.toast.cloud.clockify.domain.service.timeentry;

import com.toast.cloud.clockify.domain.model.timeentry.ClockifyProject;
import com.toast.cloud.clockify.domain.model.timeentry.ClockifyTimeEntry;
import com.toast.cloud.clockify.infrastructure.service.timeentry.TimeEntryApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClockifyTimeEntryService {

    private final TimeEntryApiService clockifyTimeEntryService;

    public Optional<ClockifyTimeEntry> getCurrentTimeEntry() {
        return clockifyTimeEntryService.getTimeEntryList(true)
            .stream()
            .map(ClockifyTimeEntry::new)
            .findFirst();
    }

    public void startTimer(ZonedDateTime start, String description, String projectId) {
        clockifyTimeEntryService.startTimer(start, description, projectId);
    }

    public void stopTimer() {
        clockifyTimeEntryService.stopTimer();
    }

    public List<ClockifyProject> getProjects() {
        return clockifyTimeEntryService.getProjects()
            .stream()
            .map(ClockifyProject::new)
            .collect(Collectors.toList());
    }

}