package com.toast.cloud.dpbm.domain.model.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMilestoneRepository extends JpaRepository<ProjectMilestone, ProjectMilestoneId>, ProjectMilestoneRepositoryCustom {

}
