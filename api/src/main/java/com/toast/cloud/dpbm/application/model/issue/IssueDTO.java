package com.toast.cloud.dpbm.application.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class IssueDTO {

    private String issueId;
    private String parentIssueId;
    private String projectId;
    private String title;
    private String moduleId;
    private String workingTypeId;
    private BigDecimal mandays;
    private String workflowId;
    private WorkflowTypeCode workflowTypeCode;
    private String milestoneId;

    @Builder
    public IssueDTO(@NonNull String issueId,
                    String parentIssueId,
                    @NonNull String projectId,
                    @NonNull String title,
                    String moduleId,
                    String workingTypeId,
                    BigDecimal mandays,
                    @NonNull String workflowId,
                    @NonNull WorkflowTypeCode workflowTypeCode,
                    String milestoneId) {
        this.issueId = issueId;
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
