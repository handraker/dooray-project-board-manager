package com.toast.cloud.dpbm.application.model.board;

import com.toast.cloud.dpbm.application.model.common.DateProgress;
import com.toast.cloud.dpbm.application.model.issue.ParentIssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.statistics.IssueWorkflowStatistics;
import lombok.Getter;

@Getter
public class ParentIssueBoardItem implements Comparable {

    private ParentIssueDTO parentIssue;
    private IssueWorkflowStatistics mandayStatistics;
    private IssueWorkflowStatistics countStatistics;
    private DateProgress devDateProgress;
    private DateProgress deployDateProgress;

    public ParentIssueBoardItem(ParentIssue parentIssue,
                                IssueWorkflowStatistics mandayStatistics,
                                IssueWorkflowStatistics countStatistics) {
        this.parentIssue = new ParentIssueDTO(parentIssue);
        this.mandayStatistics = mandayStatistics;
        this.countStatistics = countStatistics;
        this.devDateProgress = new DateProgress(parentIssue.getDevStartDate(), parentIssue.getDevEndDate());
        this.deployDateProgress = new DateProgress(parentIssue.getDeployStartDate(), parentIssue.getDeployEndDate());
    }

    @Override
    public int compareTo(Object o) {
        ParentIssueBoardItem other = (ParentIssueBoardItem)o;
        if (parentIssue.getDevStartDate() == null) {
            return 1;
        }

        if (other.parentIssue.getDevStartDate() == null) {
            return -1;
        }

        int result = parentIssue.getDevStartDate().compareTo(other.parentIssue.getDevStartDate());
        if (result == 0) {
            return parentIssue.getDevEndDate().compareTo(other.parentIssue.getDevEndDate());
        } else {
            return result;
        }
    }

}
