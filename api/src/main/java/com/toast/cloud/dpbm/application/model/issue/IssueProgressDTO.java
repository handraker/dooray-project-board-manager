package com.toast.cloud.dpbm.application.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
public class IssueProgressDTO {

    private DevStatusCode devStatusCode;
    private LocalDate devStartDate;
    private LocalDate devEndDate;
    private DevStatusCode deployStatusCode;
    private LocalDate deployStartDate;
    private LocalDate deployEndDate;

    @Builder
    public IssueProgressDTO(@NonNull DevStatusCode devStatusCode,
                            LocalDate devStartDate,
                            LocalDate devEndDate,
                            @NonNull DevStatusCode deployStatusCode,
                            LocalDate deployStartDate,
                            LocalDate deployEndDate) {
        this.devStatusCode = devStatusCode;
        this.devStartDate = devStartDate;
        this.devEndDate = devEndDate;
        this.deployStatusCode = deployStatusCode;
        this.deployStartDate = deployStartDate;
        this.deployEndDate = deployEndDate;
    }

}
