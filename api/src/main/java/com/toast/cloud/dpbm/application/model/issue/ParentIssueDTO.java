package com.toast.cloud.dpbm.application.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ParentIssueDTO {

    private String parentIssueId;
    private String projectId;
    private String title;
    private int issueNo;
    private String moduleId;
    private DevStatusCode devStatusCode;
    private LocalDate devStartDate;
    private LocalDate devEndDate;
    private DevStatusCode deployStatusCode;
    private LocalDate deployStartDate;
    private LocalDate deployEndDate;
    private String milestoneId;

    public ParentIssueDTO(ParentIssue parentIssue) {
        this.parentIssueId = parentIssue.getId();
        this.projectId = parentIssue.getProjectId();
        this.title = parentIssue.getTitle();
        this.issueNo = parentIssue.getIssueNo();
        this.moduleId = parentIssue.getModuleId();
        this.devStatusCode = parentIssue.getDevStatusCode();
        this.devStartDate = parentIssue.getDevStartDate();
        this.devEndDate = parentIssue.getDevEndDate();
        this.deployStatusCode = parentIssue.getDeployStatusCode();
        this.deployStartDate = parentIssue.getDeployStartDate();
        this.deployEndDate = parentIssue.getDeployEndDate();
        this.milestoneId = parentIssue.getMilestoneId();
    }

    @Builder
    public ParentIssueDTO(@NonNull String parentIssueId,
                          String projectId,
                          @NonNull String title,
                          int issueNo,
                          String moduleId,
                          @NonNull DevStatusCode devStatusCode,
                          LocalDate devStartDate,
                          LocalDate devEndDate,
                          @NonNull DevStatusCode deployStatusCode,
                          LocalDate deployStartDate,
                          LocalDate deployEndDate,
                          String milestoneId) {
        this.parentIssueId = parentIssueId;
        this.projectId = projectId;
        this.title = title;
        this.issueNo = issueNo;
        this.moduleId = moduleId;
        this.devStatusCode = devStatusCode;
        this.devStartDate = devStartDate;
        this.devEndDate = devEndDate;
        this.deployStatusCode = deployStatusCode;
        this.deployStartDate = deployStartDate;
        this.deployEndDate = deployEndDate;
        this.milestoneId = milestoneId;
    }

}
