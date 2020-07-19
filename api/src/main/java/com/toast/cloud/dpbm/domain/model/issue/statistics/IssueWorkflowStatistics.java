package com.toast.cloud.dpbm.domain.model.issue.statistics;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class IssueWorkflowStatistics {

    private BigDecimal totalValue;
    private List<IssueWorkflowStatisticsItem> items;

    public IssueWorkflowStatistics(List<IssueWorkflowStatisticsItem> items) {
        this.totalValue = items.stream()
            .map(IssueWorkflowStatisticsItem::getValue)
            .reduce(BigDecimal.ZERO, (l, r) -> l.add(r));
        this.items = items.stream()
            .map(item -> item.calculateRatio(totalValue))
            .sorted()
            .collect(Collectors.toList());
    }

}
