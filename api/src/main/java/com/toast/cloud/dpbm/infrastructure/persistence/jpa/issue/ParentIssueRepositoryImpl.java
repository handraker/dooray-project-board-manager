package com.toast.cloud.dpbm.infrastructure.persistence.jpa.issue;

import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssueRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ParentIssueRepositoryImpl extends QuerydslRepositorySupport implements ParentIssueRepositoryCustom {

    public ParentIssueRepositoryImpl() {
        super(ParentIssue.class);
    }

}
