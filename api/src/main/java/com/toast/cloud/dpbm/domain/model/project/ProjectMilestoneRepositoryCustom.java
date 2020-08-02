package com.toast.cloud.dpbm.domain.model.project;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProjectMilestoneRepositoryCustom {

    List<ProjectMilestone> findByProjectId(String projectId);

}
