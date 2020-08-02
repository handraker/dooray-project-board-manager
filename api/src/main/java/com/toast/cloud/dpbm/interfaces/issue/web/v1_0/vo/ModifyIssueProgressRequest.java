package com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo;

import com.toast.cloud.dpbm.application.model.issue.IssueProgressDTO;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ModifyIssueProgressRequest {

    @NotNull
    private DevStatusCode devStatusCode;
    private LocalDate devStartDate;
    private LocalDate devEndDate;
    @NotNull
    private DevStatusCode deployStatusCode;
    private LocalDate deployStartDate;
    private LocalDate deployEndDate;

    public IssueProgressDTO getIssueProgressDTO() {
        return IssueProgressDTO.builder()
            .devStatusCode(devStatusCode)
            .devStartDate(devStartDate)
            .devEndDate(devEndDate)
            .deployStatusCode(deployStatusCode)
            .deployStartDate(deployStartDate)
            .deployEndDate(deployEndDate)
            .build();
    }

}
