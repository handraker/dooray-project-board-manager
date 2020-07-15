package com.toast.cloud.dpbm.domain.model.issue;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang.StringUtils;

public class ParentIssuePredicate {

    public static Predicate where(String projectId, String moduleId, String milestoneId) {
        QParentIssue parentIssue = QParentIssue.parentIssue;

        BooleanBuilder where = new BooleanBuilder();
        where.and(parentIssue.projectId.eq(projectId));

        if (StringUtils.isNotEmpty(moduleId)) {
            where.and(parentIssue.moduleId.eq(moduleId));
        }

        if (StringUtils.isNotEmpty(milestoneId)) {
            where.and(parentIssue.milestoneId.eq(milestoneId));
        }

        return where;
    }

}
