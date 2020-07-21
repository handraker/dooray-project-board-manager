package com.toast.cloud.dpbm.application.service.issue;

import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class IssueAppService {

    private final IssueRepository issueRepository;

    @Transactional
    public void create(IssueDTO issueDTO) {
        issueRepository.findById(issueDTO.getIssueId()).ifPresent(issueRepository::delete);

        Issue issue = Issue.factory()
            .id(issueDTO.getIssueId())
            .parentIssueId(issueDTO.getParentIssueId())
            .projectId(issueDTO.getProjectId())
            .title(issueDTO.getTitle())
            .moduleId(issueDTO.getModuleId())
            .workingTypeId(issueDTO.getWorkingTypeId())
            .mandays(issueDTO.getMandays())
            .workflowId(issueDTO.getWorkflowId())
            .workflowTypeCode(issueDTO.getWorkflowTypeCode())
            .milestoneId(issueDTO.getMilestoneId())
            .newInstance();
        issueRepository.save(issue);
    }

}
