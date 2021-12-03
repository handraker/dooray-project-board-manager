package com.toast.cloud.dpbm.application.service.issue;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.clockify.domain.model.timeentry.ClockifyTimeEntry;
import com.toast.cloud.clockify.domain.service.timeentry.ClockifyTimeEntryService;
import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.application.model.issue.IssueProgressDTO;
import com.toast.cloud.dpbm.domain.model.issue.*;
import com.toast.cloud.dpbm.domain.service.issue.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IssueAppService {

    private final IssueService issueService;
    private final IssueRepository issueRepository;
    private final IssueProgressRepository issueProgressRepository;
    private final ClockifyTimeEntryService clockifyTimeEntryService;

    public Optional<IssueDTO> getIssue(String issueId) {
        return issueRepository.findById(issueId)
            .map(issue -> new IssueDTO(issue, clockifyTimeEntryService.getCurrentTimeEntry()));
    }

    public List<IssueDTO> getIssues(String memberId, Predicate predicate) {
        Optional<ClockifyTimeEntry> timeEntryOptional = Optional.ofNullable(memberId)
            .filter(id -> id.equals("1387695629192606464"))
            .flatMap(id -> clockifyTimeEntryService.getCurrentTimeEntry());

        return issueRepository.findByPredicate(predicate)
            .stream()
            .map(issue -> new IssueDTO(issue, timeEntryOptional))
            .collect(Collectors.toList());
    }

    public void startTimer(String issueId, String moduleName) {
        issueService.startTimer(issueId, moduleName);
    }

    public void stopTimer() {
        clockifyTimeEntryService.stopTimer();
    }

    @Transactional
    public void deleteIssue(String issueId) {
        issueRepository.deleteByIssueId(issueId);
    }

    @Transactional
    public void deleteChildIssue(String parentIssueId) {
        issueRepository.deleteByParentIssue(parentIssueId);
    }

    @Transactional
    public void modifyIssueProgress(String issueId, IssueProgressDTO issueProgressDTO) {
        IssueProgress newIssueProgress = issueProgressRepository.findById(issueId)
            .map(issueProgress -> issueProgress.modify(issueProgressDTO))
            .orElseGet(() -> new IssueProgress(issueId, issueProgressDTO));
        issueProgressRepository.save(newIssueProgress);
    }

    @Transactional
    public void create(List<IssueDTO> issueDTOList) {
        List<Issue> issueList = issueDTOList.stream()
            .map(issueDTO -> {
                issueRepository.findById(issueDTO.getIssueId()).ifPresent(issueRepository::delete);
                return Issue.factory()
                    .id(issueDTO.getIssueId())
                    .parentIssueId(issueDTO.getParentIssueId())
                    .projectId(issueDTO.getProjectId())
                    .title(issueDTO.getTitle())
                    .issueNo(issueDTO.getIssueNo())
                    .memberId(issueDTO.getMemberId())
                    .moduleId(issueDTO.getModuleId())
                    .workingTypeId(issueDTO.getWorkingTypeId())
                    .mandays(issueDTO.getMandays())
                    .workflow(new Workflow(issueDTO.getWorkflowId(), issueDTO.getWorkflowTypeCode()))
                    .milestoneId(issueDTO.getMilestoneId())
                    .tagIdList(issueDTO.getTagIdList())
                    .updatedAt(issueDTO.getUpdatedAt())
                    .newInstance();
            })
            .collect(Collectors.toList());

        issueRepository.saveAll(issueList);
    }

}
