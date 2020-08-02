package com.toast.cloud.dpbm.domain.model.project;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Embeddable
public class ProjectMilestoneId implements Serializable {

    private String projectId;
    private String milestoneId;

}
