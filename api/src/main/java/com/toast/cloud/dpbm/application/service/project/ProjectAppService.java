package com.toast.cloud.dpbm.application.service.project;

import com.toast.cloud.dpbm.application.model.project.ProjectDTO;
import com.toast.cloud.dpbm.application.model.project.ProjectMilestoneDTO;
import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.IssuePredicate;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import com.toast.cloud.dpbm.domain.model.project.ProjectMilestone;
import com.toast.cloud.dpbm.domain.model.project.ProjectMilestoneId;
import com.toast.cloud.dpbm.domain.model.project.ProjectMilestoneRepository;
import com.toast.cloud.dpbm.domain.model.project.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProjectAppService {

    private final ProjectRepository projectRepository;
    private final ProjectMilestoneRepository projectMilestoneRepository;
    private final IssueRepository issueRepository;

    public ProjectDTO getProject(String projectId) {
        return projectRepository.findById(projectId)
            .map(ProjectDTO::new)
            .orElseGet(() -> new ProjectDTO(projectId));
    }

    public List<ProjectMilestoneDTO> getProjectMilestones(String projectId) {
        return projectMilestoneRepository.findByProjectId(projectId)
            .stream()
            .map(ProjectMilestoneDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void createProjectMilestones(List<ProjectMilestoneDTO> projectMilestones) {
        projectMilestones.stream()
            .map(dto -> {
                ProjectMilestoneId id = new ProjectMilestoneId(dto.getProjectId(), dto.getMilestoneId());
                return projectMilestoneRepository.findById(id)
                    .map(projectMilestone -> projectMilestone.setCodeFreezeDate(dto.getCodeFreezeDate()))
                    .orElseGet(() -> ProjectMilestone.factory()
                        .id(id)
                        .codeFreezeDate(dto.getCodeFreezeDate())
                        .newInstance());
            })
            .map(projectMilestoneRepository::save)
            .filter(projectMilestone -> projectMilestone.getCodeFreezeDate() != null)
            .forEach(projectMilestone -> {
                List<Issue> issueList = issueRepository.findByPredicate(IssuePredicate.builder()
                                                                            .milestoneId(projectMilestone.getId().getMilestoneId())
                                                                            .build());
                issueList.forEach(issue -> issue.modifyByCodeFreeze(projectMilestone.getCodeFreezeDate()));
            });
    }

}
