package com.toast.cloud.dpbm.application.model.board;

import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneBoard {

    private IssueWorkflowStatistics mandayStatistics;
    private IssueWorkflowStatistics countStatistics;

}
