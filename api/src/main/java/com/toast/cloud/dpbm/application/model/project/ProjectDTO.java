package com.toast.cloud.dpbm.application.model.project;

import com.toast.cloud.dpbm.domain.model.project.Project;
import lombok.Getter;

@Getter
public class ProjectDTO {

    private String projectId;
    private String doorayModuleTagPrefixId;
    private String doorayWorkingTypeTagPrefixId;
    private String doorayMandaysTagPrefixId;
    private String department;

    public ProjectDTO(String projectId) {
        this.projectId = projectId;
    }

    public ProjectDTO(Project project) {
        this.projectId = project.getId();
        this.doorayModuleTagPrefixId = project.getDoorayModuleTagPrefixId();
        this.doorayWorkingTypeTagPrefixId = project.getDoorayWorkingTypeTagPrefixId();
        this.doorayMandaysTagPrefixId = project.getDoorayMandaysTagPrefixId();
        this.department = project.getDepartment();
    }

}
