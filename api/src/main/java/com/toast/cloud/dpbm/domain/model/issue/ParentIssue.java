package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dpbm_parent_issue")
@Entity
public class ParentIssue extends AbstractBaseEntity<String> {

    private String projectId;
    private String title;
    private String moduleId;
    @Enumerated(EnumType.STRING)
    private DevStatusCode devStatusCode;
    private LocalDate devStartDate;
    private LocalDate devEndDate;
    @Enumerated(EnumType.STRING)
    private DevStatusCode deployStatusCode;
    private LocalDate deployStartDate;
    private LocalDate deployEndDate;
    private String milestoneId;

    @Builder(builderMethodName = "factory", buildMethodName = "newInstance")
    private ParentIssue(@NonNull String id, @NonNull String projectId, @NonNull String title, String moduleId) {
        generateId(id);
        this.projectId = projectId;
        this.title = title;
        this.moduleId = moduleId;
        this.devStatusCode = DevStatusCode.WAITING;
        this.deployStatusCode = DevStatusCode.WAITING;
    }

}
