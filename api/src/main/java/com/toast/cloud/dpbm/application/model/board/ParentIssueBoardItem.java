package com.toast.cloud.dpbm.application.model.board;

import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ParentIssueBoardItem {

    private ParentIssue parentIssue;
    private IssueWorkflowStatistics issueWorkflowStatistics;
    private BigDecimal remainingMandays;

}
