package com.toast.cloud.dpbm.domain.service.issue;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.toast.cloud.clockify.domain.model.timeentry.ClockifyProject;
import com.toast.cloud.clockify.domain.service.timeentry.ClockifyTimeEntryService;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import com.toast.cloud.dpbm.domain.model.issue.Workflow;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final ClockifyTimeEntryService clockifyTimeEntryService;
    private final Cache<String, String> projectIdCache = CacheBuilder.newBuilder()
        .expireAfterWrite(1, TimeUnit.HOURS)
        .build();

    public void startTimer(String issueId, String moduleName) {
        issueRepository.findById(issueId).ifPresent(issue -> {
            try {
                String projectId = projectIdCache.get(moduleName, () -> clockifyTimeEntryService.getProjects()
                    .stream()
                    .filter(project -> project.getName().contains(moduleName))
                    .findFirst()
                    .map(ClockifyProject::getId)
                    .orElse(""));
                clockifyTimeEntryService.startTimer(ZonedDateTime.now(ZoneOffset.UTC).withNano(0),
                                                    issue.getClockifyDescription(),
                                                    projectId);
            } catch (ExecutionException e) {
                clockifyTimeEntryService.startTimer(ZonedDateTime.now(ZoneOffset.UTC).withNano(0), issue.getClockifyDescription(), "");
            }
        });
    }

    public void changeWorkflow(String issueId, Workflow workflow) {
        issueRepository.findById(issueId).ifPresent(issue -> {
            issue.setWorkflow(workflow);
            issueRepository.save(issue);
        });
    }

}
