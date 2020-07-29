package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Embeddable
public class Workflow {

    private String workflowId;
    @Enumerated(EnumType.STRING)
    private WorkflowTypeCode workflowTypeCode;

    public Workflow(String workflowId, String workflowTypeCode) {
        this.workflowId = workflowId;
        this.workflowTypeCode = WorkflowTypeCode.valueOf(workflowTypeCode.toUpperCase());
    }

}
