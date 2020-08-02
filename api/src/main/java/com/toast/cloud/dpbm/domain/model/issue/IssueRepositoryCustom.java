package com.toast.cloud.dpbm.domain.model.issue;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IssueRepositoryCustom {

    List<Issue> findParentIssue(Predicate predicate);

    List<Issue> findByPredicate(Predicate predicate);

    void deleteByIssueId(String issueId);

    void deleteByParentIssue(String parentIssueId);

    IssueWorkflowStatistics getMandaysIssueWorkflowStatistics(Predicate predicate);

    IssueWorkflowStatistics getCountIssueWorkflowStatistics(Predicate predicate);

}
