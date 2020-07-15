package com.toast.cloud.dpbm.domain.model.issue.statistics;

import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Getter
public class IssueWorkflowStatisticsItem {

    private final WorkflowTypeCode workflowTypeCode;
    private final BigDecimal mandays;
    private int mandaysRatio;
    private final long count;
    private int countRatio;

    public IssueWorkflowStatisticsItem(WorkflowTypeCode workflowTypeCode, BigDecimal mandays, long count) {
        this.workflowTypeCode = workflowTypeCode;
        this.mandays = mandays;
        this.count = count;
    }

    public IssueWorkflowStatisticsItem calculateRatio(BigDecimal totalMandays, long totalCount) {
        this.mandaysRatio = Optional.ofNullable(totalMandays)
            .filter(total -> total.compareTo(BigDecimal.ZERO) != 0)
            .map(total -> mandays
                .divide(total, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue())
            .orElse(0);
        this.countRatio = Optional.of(totalCount)
            .filter(total -> total != 0)
            .map(total -> BigDecimal.valueOf(count)
                .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue())
            .orElse(0);
        return this;
    }

}
