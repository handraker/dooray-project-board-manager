package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.converter.LocalDateConverter;
import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import com.toast.cloud.dpbm.application.model.issue.IssueProgressDTO;
import com.toast.cloud.dpbm.domain.model.issue.code.DevStatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "dpbm_issue_progress")
@Entity
public class IssueProgress extends AbstractBaseEntity<String> {

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

    public IssueProgress(String issueId, IssueProgressDTO issueProgressDTO) {
        generateId(issueId);
        modify(issueProgressDTO);
    }

    public IssueProgress(String issueId, LocalDate codeFreezeDate) {
        generateId(issueId);
        modifyByCodeFreeze(codeFreezeDate);
        this.devStatusCode = DevStatusCode.WAITING;
        this.deployStatusCode = DevStatusCode.WAITING;
    }

    public IssueProgress modify(IssueProgressDTO issueProgressDTO) {
        this.devStatusCode = issueProgressDTO.getDevStatusCode();
        this.devStartDate = issueProgressDTO.getDevStartDate();
        this.devEndDate = issueProgressDTO.getDevEndDate();
        this.deployStatusCode = issueProgressDTO.getDeployStatusCode();
        this.deployStartDate = issueProgressDTO.getDeployStartDate();
        this.deployEndDate = issueProgressDTO.getDeployEndDate();
        return this;
    }

    public void modifyByCodeFreeze(LocalDate codeFreezeDate) {
        if (devStartDate == null) {
            this.devStartDate = codeFreezeDate
                .withDayOfMonth(1)
                .with(firstInMonth(DayOfWeek.MONDAY))
                .plusWeeks(1)
                .plusDays(1);
            this.devEndDate = codeFreezeDate;
        }

        if (deployStartDate == null) {
            this.deployStartDate = codeFreezeDate.plusDays(3);
            this.deployEndDate = deployStartDate.plusWeeks(2)
                .plusDays(1);
        }
    }

}
