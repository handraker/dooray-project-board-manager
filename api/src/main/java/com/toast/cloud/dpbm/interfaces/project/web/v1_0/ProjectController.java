package com.toast.cloud.dpbm.interfaces.project.web.v1_0;

import com.toast.cloud.dpbm.application.model.project.ProjectDTO;
import com.toast.cloud.dpbm.application.model.project.ProjectMilestoneDTO;
import com.toast.cloud.dpbm.application.service.project.ProjectAppService;
import com.toast.cloud.dpbm.interfaces.project.web.v1_0.vo.CreateProjectMilestoneRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class ProjectController {

    private final ProjectAppService projectAppService;

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/projects/{projectId}")
    public ProjectDTO getProject(@PathVariable("projectId") String projectId) {
        return projectAppService.getProject(projectId);
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/projects/{projectId}/milestones")
    public List<ProjectMilestoneDTO> getProjectMilestones(@PathVariable("projectId") String projectId) {
        return projectAppService.getProjectMilestones(projectId);
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @PostMapping("/dpbm/projects/{projectId}/milestones")
    public void createProjectMilestones(@PathVariable("projectId") String projectId,
                                        @Valid @RequestBody CreateProjectMilestoneRequest request) {
        projectAppService.createProjectMilestones(request.getProjectMilestones()
                                                      .stream()
                                                      .map(projectMilestone -> ProjectMilestoneDTO.builder()
                                                          .projectId(projectId)
                                                          .milestoneId(projectMilestone.getMilestoneId())
                                                          .codeFreezeDate(projectMilestone.getCodeFreezeDate())
                                                          .build())
                                                      .collect(Collectors.toList()));
    }

}