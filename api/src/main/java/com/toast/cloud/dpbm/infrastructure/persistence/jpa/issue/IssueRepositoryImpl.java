package com.toast.cloud.dpbm.infrastructure.persistence.jpa.issue;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.toast.cloud.dpbm.domain.model.issue.*;
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
    public List<Issue> findParentIssue(Predicate predicate) {
        QIssue issue = QIssue.issue;
        QIssueProgress issueProgress = QIssueProgress.issueProgress;
        QIssueTag issueTag = QIssueTag.issueTag;

        return from(issue)
            .distinct()
            .leftJoin(issue.progress, issueProgress)
            .fetchJoin()
            .leftJoin(issue.issueTagList, issueTag)
            .fetchJoin()
            .where(issue.parentIssueId.isNull().and(predicate))
            .orderBy(issue.moduleId.asc(), issue.id.asc())
            .fetch();
    }

    @Override
    public List<Issue> findByPredicate(Predicate predicate) {
        QIssue issue = QIssue.issue;
        QIssueProgress issueProgress = QIssueProgress.issueProgress;
        QIssueTag issueTag = QIssueTag.issueTag;

        return from(issue)
            .distinct()
            .leftJoin(issue.progress, issueProgress)
            .fetchJoin()
            .leftJoin(issue.issueTagList, issueTag)
            .fetchJoin()
            .where(predicate)
            .orderBy(issue.updatedAt.desc())
            .fetch();
    }

    @Override
    public void deleteByIssueId(String issueId) {
        QIssue issue = QIssue.issue;
        QIssueTag issueTag = QIssueTag.issueTag;

        delete(issueTag)
            .where(issueTag.issue.id.eq(issueId))
            .execute();

        delete(issue)
            .where(issue.id.eq(issueId))
            .execute();
    }

    @Override
    public void deleteByParentIssue(String parentIssueId) {
        QIssue issue = QIssue.issue;
        QIssueTag issueTag = QIssueTag.issueTag;

        delete(issueTag)
            .where(JPAExpressions.selectFrom(issue)
                       .where(issue.parentIssueId.eq(parentIssueId)
                                  .and(issue.id.eq(issueTag.issue.id)))
                       .exists())
            .execute();

        delete(issue)
            .where(issue.parentIssueId.eq(parentIssueId))
            .execute();
    }

    @Override
    public IssueWorkflowStatistics getMandaysIssueWorkflowStatistics(Predicate predicate) {
        QIssue issue = QIssue.issue;

        List<IssueWorkflowStatisticsItem> items = from(issue)
            .select(Projections.constructor(IssueWorkflowStatisticsItem.class,
                                            issue.workflow.workflowId,
                                            issue.workflow.workflowTypeCode,
                                            issue.mandays.sum()))
            .where(predicate)
            .groupBy(issue.workflow.workflowId, issue.workflow.workflowTypeCode)
            .fetch();
        return new IssueWorkflowStatistics(items);
    }

    @Override
    public IssueWorkflowStatistics getCountIssueWorkflowStatistics(Predicate predicate) {
        QIssue issue = QIssue.issue;

        List<IssueWorkflowStatisticsItem> items = from(issue)
            .select(Projections.constructor(IssueWorkflowStatisticsItem.class,
                                            issue.workflow.workflowId,
                                            issue.workflow.workflowTypeCode,
                                            issue.count()))
            .where(predicate)
            .groupBy(issue.workflow.workflowId, issue.workflow.workflowTypeCode)
            .fetch();
        return new IssueWorkflowStatistics(items);
    }

}
