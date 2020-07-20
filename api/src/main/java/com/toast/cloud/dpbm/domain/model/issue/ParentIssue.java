package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.converter.LocalDateConverter;
import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dpbm_parent_issue")
@Entity
public class ParentIssue extends AbstractBaseEntity<String> {

    private String projectId;
    @Setter
    private String title;
    @Setter
    private String moduleId;
    @Enumerated(EnumType.STRING)
    private DevStatusCode devStatusCode;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate devStartDate;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate devEndDate;
    @Enumerated(EnumType.STRING)
    private DevStatusCode deployStatusCode;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate deployStartDate;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate deployEndDate;
    @Setter
    private String milestoneId;

    @Builder(builderMethodName = "factory", buildMethodName = "newInstance")
    private ParentIssue(@NonNull String id,
                        @NonNull String projectId,
                        @NonNull String title,
                        String moduleId,
                        @NonNull DevStatusCode devStatusCode,
                        LocalDate devStartDate,
                        LocalDate devEndDate,
                        @NonNull DevStatusCode deployStatusCode,
                        LocalDate deployStartDate,
                        LocalDate deployEndDate,
                        String milestoneId) {
        generateId(id);
        this.projectId = projectId;
        this.title = title;
        this.moduleId = moduleId;
        this.devStatusCode = devStatusCode;
        this.devStartDate = devStartDate;
        this.devEndDate = devEndDate;
        this.deployStatusCode = deployStatusCode;
        this.deployStartDate = deployStartDate;
        this.deployEndDate = deployEndDate;
        this.milestoneId = milestoneId;
    }

    public void modify(String title,
                       String moduleId,
                       DevStatusCode devStatusCode,
                       LocalDate devStartDate,
                       LocalDate devEndDate,
                       DevStatusCode deployStatusCode,
                       LocalDate deployStartDate,
                       LocalDate deployEndDate,
                       String milestoneId) {
        this.title = title;
        this.moduleId = moduleId;
        this.devStatusCode = devStatusCode;
        this.devStartDate = devStartDate;
        this.devEndDate = devEndDate;
        this.deployStatusCode = deployStatusCode;
        this.deployStartDate = deployStartDate;
        this.deployEndDate = deployEndDate;
        this.milestoneId = milestoneId;
    }

}
