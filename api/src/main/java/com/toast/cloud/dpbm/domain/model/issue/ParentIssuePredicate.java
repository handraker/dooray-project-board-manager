package com.toast.cloud.dpbm.domain.model.issue;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.Builder;
import org.apache.commons.lang.StringUtils;

public class ParentIssuePredicate {

    @Builder
    private static Predicate where(String projectId, String milestoneId, String moduleId) {
        QParentIssue parentIssue = QParentIssue.parentIssue;

        BooleanBuilder where = new BooleanBuilder();
        where.and(parentIssue.projectId.eq(projectId));

        if (StringUtils.isNotEmpty(milestoneId)) {
            where.and(parentIssue.milestoneId.eq(milestoneId));
        }

        if (StringUtils.isNotEmpty(moduleId)) {
            where.and(parentIssue.moduleId.eq(moduleId));
        }

        return where;
    }

}
