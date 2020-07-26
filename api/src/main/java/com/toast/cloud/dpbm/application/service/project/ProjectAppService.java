package com.toast.cloud.dpbm.application.service.project;

import com.toast.cloud.dpbm.application.model.project.ProjectDTO;
import com.toast.cloud.dpbm.domain.model.project.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProjectAppService {

    private final ProjectRepository projectRepository;

    public ProjectDTO getProject(String projectId) {
        return projectRepository.findById(projectId)
            .map(ProjectDTO::new)
            .orElseGet(() -> new ProjectDTO(projectId));
    }

}
