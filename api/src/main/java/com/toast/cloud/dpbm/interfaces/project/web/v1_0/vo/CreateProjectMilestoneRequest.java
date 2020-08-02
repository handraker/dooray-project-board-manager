package com.toast.cloud.dpbm.interfaces.project.web.v1_0.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateProjectMilestoneRequest {

    @Size(min = 1)
    @NotNull
    private List<ProjectMilestone> projectMilestones;

    @Getter
    @Setter
    public static class ProjectMilestone {

        @NotNull
        private String milestoneId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate codeFreezeDate;

    }

}
