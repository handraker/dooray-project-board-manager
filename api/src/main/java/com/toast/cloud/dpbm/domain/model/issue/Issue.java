package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.*;

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

    @Builder(builderMethodName = "factory", buildMethodName = "newInstance")
    private Issue(String id,
                  String parentIssueId,
                  @NonNull String projectId,
                  @NonNull String title,
                  String moduleId,
                  String workingTypeId,
                  BigDecimal mandays,
                  @NonNull String workflowId,
                  @NonNull WorkflowTypeCode workflowTypeCode,
                  String milestoneId) {
        generateId(id);
        this.parentIssueId = parentIssueId;
        this.projectId = projectId;
        this.title = title;
        this.moduleId = moduleId;
        this.workingTypeId = workingTypeId;
        this.mandays = mandays;
        this.workflowId = workflowId;
        this.workflowTypeCode = workflowTypeCode;
        this.milestoneId = milestoneId;
    }

}
