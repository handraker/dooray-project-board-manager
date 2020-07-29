package com.toast.cloud.dpbm.domain.service.issue;

import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import com.toast.cloud.dpbm.domain.model.issue.Workflow;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public void changeWorkflow(String issueId, Workflow workflow) {
        issueRepository.findById(issueId).ifPresent(issue -> {
            issue.setWorkflow(workflow);
            issueRepository.save(issue);
        });
    }

}
