package com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo;

import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateIssueRequest {

    @NotNull
    private String issueId;
    private String parentIssueId;
    @NotNull
    private String projectId;
    @NotNull
    private String title;
    private String moduleId;
    private String workingTypeId;
    private BigDecimal mandays;
    @NotNull
    private String workflowId;
    @NotNull
    private WorkflowTypeCode workflowTypeCode;
    private String milestoneId;

    public IssueDTO getIssueDTO() {
        return IssueDTO.builder()
            .issueId(issueId)
            .parentIssueId(parentIssueId)
            .projectId(projectId)
            .title(title)
            .moduleId(moduleId)
            .workingTypeId(workingTypeId)
            .mandays(mandays)
            .workflowId(workflowId)
            .workflowTypeCode(workflowTypeCode)
            .milestoneId(milestoneId)
            .build();
    }

}
