package com.toast.cloud.dpbm.interfaces.project.web.v1_0;

import com.toast.cloud.dpbm.application.model.project.ProjectDTO;
import com.toast.cloud.dpbm.application.service.project.ProjectAppService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ProjectController {

    private final ProjectAppService projectAppService;

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/projects/{projectId}")
    public ProjectDTO getProject(@PathVariable("projectId") String projectId) {
        return projectAppService.getProject(projectId);
    }

}