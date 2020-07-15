package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IssueRepositoryCustom {

    IssueWorkflowStatistics getIssueWorkflowStatistics(String parentIssueId);

}
