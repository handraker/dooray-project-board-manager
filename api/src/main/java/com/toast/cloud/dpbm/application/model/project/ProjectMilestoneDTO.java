package com.toast.cloud.dpbm.application.model.project;

import com.toast.cloud.dpbm.domain.model.project.ProjectMilestone;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
public class ProjectMilestoneDTO {

    private String projectId;
    private String milestoneId;
    private LocalDate codeFreezeDate;

    public ProjectMilestoneDTO(ProjectMilestone projectMilestone) {
        this.projectId = projectMilestone.getId().getProjectId();
        this.milestoneId = projectMilestone.getId().getMilestoneId();
        this.codeFreezeDate = projectMilestone.getCodeFreezeDate();
    }

    @Builder
    public ProjectMilestoneDTO(@NonNull String projectId,
                               @NonNull String milestoneId,
                               LocalDate codeFreezeDate) {
        this.projectId = projectId;
        this.milestoneId = milestoneId;
        this.codeFreezeDate = codeFreezeDate;
    }

}
