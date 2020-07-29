package com.toast.cloud.dpbm.domain.model.issue;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Builder;
import org.apache.commons.lang.StringUtils;

import java.time.ZonedDateTime;

public class IssuePredicate {

    @Builder
    private static Predicate where(String parentIssueId,
                                   String projectId,
                                   String milestoneId,
                                   String moduleId,
                                   String memberId,
                                   WorkflowTypeCode workflowTypeCode,
                                   ZonedDateTime from,
                                   ZonedDateTime to) {
        QIssue issue = QIssue.issue;

        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.isNotEmpty(parentIssueId)) {
            where.and(issue.parentIssueId.eq(parentIssueId));
        }

        if (StringUtils.isNotEmpty(projectId)) {
            where.and(issue.projectId.eq(projectId));
        }

        if (StringUtils.isNotEmpty(memberId)) {
            where.and(issue.memberId.eq(memberId));
        }

        if (workflowTypeCode != null) {
            where.and(issue.workflow.workflowTypeCode.eq(workflowTypeCode));
        }

        if (StringUtils.isNotEmpty(milestoneId)) {
            where.and(issue.milestoneId.eq(milestoneId));
        }

        if (StringUtils.isNotEmpty(moduleId)) {
            where.and(issue.moduleId.eq(moduleId));
        }

        if (from != null) {
            where.and(issue.updatedAt.goe(from));
        }

        if (to != null) {
            where.and(issue.updatedAt.lt(to));
        }

        return where;
    }

}
