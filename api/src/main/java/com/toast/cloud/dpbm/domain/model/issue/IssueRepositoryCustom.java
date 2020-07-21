package com.toast.cloud.dpbm.domain.model.issue;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IssueRepositoryCustom {

    IssueWorkflowStatistics getMandaysIssueWorkflowStatistics(Predicate predicate);

    IssueWorkflowStatistics getCountIssueWorkflowStatistics(Predicate predicate);

}
