package com.toast.cloud.clockify.domain.model.timeentry;

import com.toast.cloud.clockify.infrastructure.service.timeentry.vo.Project;
import lombok.Getter;

@Getter
public class ClockifyProject {

    private String id;
    private String name;

    public ClockifyProject(Project project) {
        this.id = project.getId();
        this.name = project.getName();
    }

}
