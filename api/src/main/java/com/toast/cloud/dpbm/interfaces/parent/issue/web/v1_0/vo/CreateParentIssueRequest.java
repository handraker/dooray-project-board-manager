package com.toast.cloud.dpbm.interfaces.parent.issue.web.v1_0.vo;

import com.toast.cloud.dpbm.application.model.issue.ParentIssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CreateParentIssueRequest {

    @NotNull
    private String parentIssueId;
    @NotNull
    private String projectId;
    @NotNull
    private String title;
    private int issueNo;
    private String moduleId;
    @NotNull
    private DevStatusCode devStatusCode;
    private LocalDate devStartDate;
    private LocalDate devEndDate;
    @NotNull
    private DevStatusCode deployStatusCode;
    private LocalDate deployStartDate;
    private LocalDate deployEndDate;
    private String milestoneId;

    public ParentIssueDTO getParentIssueDTO() {
        return ParentIssueDTO.builder()
            .parentIssueId(parentIssueId)
            .projectId(projectId)
            .title(title)
            .issueNo(issueNo)
            .moduleId(moduleId)
            .devStatusCode(devStatusCode)
            .devStartDate(devStartDate)
            .devEndDate(devEndDate)
            .deployStatusCode(deployStatusCode)
            .deployStartDate(deployStartDate)
            .deployEndDate(deployEndDate)
            .milestoneId(milestoneId)
            .build();
    }

}
