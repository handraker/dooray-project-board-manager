package com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo;

import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class GetIssueRequest {

    @NotNull
    private String projectId;
    @NotNull
    private String memberId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate to;
    @NotNull
    private WorkflowTypeCode workflowTypeCode;

}
