package com.toast.cloud.dpbm.domain.model.issue;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Builder;
import org.apache.commons.lang.StringUtils;

public class IssuePredicate {

    @Builder
    private static Predicate where(String parentIssueId, String projectId, String milestoneId, String moduleId) {
        QIssue issue = QIssue.issue;

        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.isNotEmpty(parentIssueId)) {
            where.and(issue.parentIssueId.eq(parentIssueId));
        }

        if (StringUtils.isNotEmpty(projectId)) {
            where.and(issue.projectId.eq(projectId));
        }

        if (StringUtils.isNotEmpty(milestoneId)) {
            where.and(issue.milestoneId.eq(milestoneId));
        }

        if (StringUtils.isNotEmpty(moduleId)) {
            where.and(issue.moduleId.eq(moduleId));
        }

        return where;
    }

}
