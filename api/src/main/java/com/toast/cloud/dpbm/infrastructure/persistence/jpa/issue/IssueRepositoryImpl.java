package com.toast.cloud.dpbm.infrastructure.persistence.jpa.issue;

import com.querydsl.core.types.Projections;
import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepositoryCustom;
import com.toast.cloud.dpbm.domain.model.issue.QIssue;
import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatisticsItem;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IssueRepositoryImpl extends QuerydslRepositorySupport implements IssueRepositoryCustom {

    public IssueRepositoryImpl() {
        super(Issue.class);
    }

    @Override
    public IssueWorkflowStatistics getMandaysIssueWorkflowStatistics(String parentIssueId) {
        QIssue issue = QIssue.issue;

        List<IssueWorkflowStatisticsItem> items = from(issue)
            .select(Projections.constructor(IssueWorkflowStatisticsItem.class,
                                            issue.workflowId,
                                            issue.workflowTypeCode,
                                            issue.mandays.sum()))
            .where(issue.parentIssueId.eq(parentIssueId))
            .groupBy(issue.workflowId, issue.workflowTypeCode)
            .fetch();
        return new IssueWorkflowStatistics(items);
    }

    @Override
    public IssueWorkflowStatistics getCountIssueWorkflowStatistics(String parentIssueId) {
        QIssue issue = QIssue.issue;

        List<IssueWorkflowStatisticsItem> items = from(issue)
            .select(Projections.constructor(IssueWorkflowStatisticsItem.class, issue.workflowId, issue.workflowTypeCode, issue.count()))
            .where(issue.parentIssueId.eq(parentIssueId))
            .groupBy(issue.workflowId, issue.workflowTypeCode)
            .fetch();
        return new IssueWorkflowStatistics(items);
    }

}