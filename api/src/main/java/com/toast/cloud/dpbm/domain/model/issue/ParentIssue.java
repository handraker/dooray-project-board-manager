package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import com.toast.cloud.dpbm.domain.model.issue.code.ParentIssueStatusCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
public class ParentIssue extends AbstractBaseEntity {

    private String title;
    @Enumerated(EnumType.STRING)
    private ParentIssueStatusCode devStatusCode;
    private LocalDate devStartDate;
    private LocalDate devEndDate;
    @Enumerated(EnumType.STRING)
    private ParentIssueStatusCode deployStatusCode;
    private LocalDate deployStartDate;
    private LocalDate deployEndDate;

}
