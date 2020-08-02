package com.toast.cloud.dpbm.infrastructure.persistence.jpa.project;

import com.toast.cloud.dpbm.domain.model.project.ProjectMilestone;
import com.toast.cloud.dpbm.domain.model.project.ProjectMilestoneRepositoryCustom;
import com.toast.cloud.dpbm.domain.model.project.QProjectMilestone;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectMilestoneRepositoryImpl extends QuerydslRepositorySupport implements ProjectMilestoneRepositoryCustom {

    public ProjectMilestoneRepositoryImpl() {
        super(ProjectMilestone.class);
    }

    @Override
    public List<ProjectMilestone> findByProjectId(String projectId) {
        QProjectMilestone projectMilestone = QProjectMilestone.projectMilestone;

        return from(projectMilestone)
            .where(projectMilestone.id.projectId.eq(projectId))
            .fetch();
    }

}
