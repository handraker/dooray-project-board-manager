package com.toast.cloud.dpbm.domain.model.project;

import com.toast.cloud.common.jpa.converter.LocalDateConverter;
import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dpbm_project_milestone")
@Entity
public class ProjectMilestone extends AbstractBaseEntity<ProjectMilestoneId> {

    @Convert(converter = LocalDateConverter.class)
    private LocalDate codeFreezeDate;

    @Builder(builderMethodName = "factory", buildMethodName = "newInstance")
    private ProjectMilestone(@NonNull ProjectMilestoneId id, LocalDate codeFreezeDate) {
        generateId(id);
        this.codeFreezeDate = codeFreezeDate;
    }

    public ProjectMilestone setCodeFreezeDate(LocalDate codeFreezeDate) {
        this.codeFreezeDate = codeFreezeDate;
        return this;
    }

}
