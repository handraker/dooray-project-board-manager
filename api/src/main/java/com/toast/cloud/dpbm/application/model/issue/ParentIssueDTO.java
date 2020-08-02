package com.toast.cloud.dpbm.application.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ParentIssueDTO {

    private String issueId;
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

    public ParentIssueDTO(Issue parentIssue) {
        this.issueId = parentIssue.getId();
        this.projectId = parentIssue.getProjectId();
        this.title = parentIssue.getTitle();
        this.issueNo = parentIssue.getIssueNo();
        this.moduleId = parentIssue.getModuleId();
        this.devStatusCode = parentIssue.getProgress().getDevStatusCode();
        this.devStartDate = parentIssue.getProgress().getDevStartDate();
        this.devEndDate = parentIssue.getProgress().getDevEndDate();
        this.deployStatusCode = parentIssue.getProgress().getDeployStatusCode();
        this.deployStartDate = parentIssue.getProgress().getDeployStartDate();
        this.deployEndDate = parentIssue.getProgress().getDeployEndDate();
        this.milestoneId = parentIssue.getMilestoneId();
    }

}
