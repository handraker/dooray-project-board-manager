package com.toast.cloud.dpbm.domain.model.issue.statistics;

import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Getter
public class IssueWorkflowStatisticsItem implements Comparable {

    private final String workflowId;
    private final WorkflowTypeCode workflowTypeCode;
    private final BigDecimal value;
    private int ratio;

    public IssueWorkflowStatisticsItem(String workflowId, WorkflowTypeCode workflowTypeCode, BigDecimal value) {
        this.workflowId = workflowId;
        this.workflowTypeCode = workflowTypeCode;
        this.value = value;
    }

    public IssueWorkflowStatisticsItem(String workflowId, WorkflowTypeCode workflowTypeCode, Long value) {
        this.workflowId = workflowId;
        this.workflowTypeCode = workflowTypeCode;
        this.value = BigDecimal.valueOf(value);
    }

    public IssueWorkflowStatisticsItem calculateRatio(BigDecimal totalValue) {
        this.ratio = Optional.ofNullable(totalValue)
            .filter(total -> total.compareTo(BigDecimal.ZERO) != 0)
            .map(total -> value
                .divide(total, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue())
            .orElse(0);
        return this;
    }

    @Override
    public int compareTo(Object o) {
        IssueWorkflowStatisticsItem other = (IssueWorkflowStatisticsItem)o;
        return workflowTypeCode.compareTo(other.workflowTypeCode);
    }

}
