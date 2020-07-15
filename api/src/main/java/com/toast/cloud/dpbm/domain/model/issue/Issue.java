package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dpbm_issue")
@Entity
public class Issue extends AbstractBaseEntity<String> {

    private String parentIssueId;
    private String projectId;
    private String title;
    private String moduleId;
    private String workingTypeId;
    private BigDecimal mandays;
    private String workflowId;
    @Enumerated(EnumType.STRING)
    private WorkflowTypeCode workflowTypeCode;
    private String milestoneId;

}
