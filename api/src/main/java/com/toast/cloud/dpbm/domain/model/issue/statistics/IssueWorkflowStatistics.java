package com.toast.cloud.dpbm.domain.model.issue.statistics;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class IssueWorkflowStatistics {

    private BigDecimal totalMandays;
    private long totalCount;
    private Set<IssueWorkflowStatisticsItem> items;

    public IssueWorkflowStatistics(List<IssueWorkflowStatisticsItem> items) {
        this.totalMandays = items.stream()
            .map(IssueWorkflowStatisticsItem::getMandays)
            .reduce(BigDecimal.ZERO, (l, r) -> l.add(r));
        this.totalCount = items.stream()
            .mapToLong(IssueWorkflowStatisticsItem::getCount)
            .sum();
        this.items = items.stream()
            .map(item -> item.calculateRatio(totalMandays, totalCount))
            .collect(Collectors.toSet());
    }

}
