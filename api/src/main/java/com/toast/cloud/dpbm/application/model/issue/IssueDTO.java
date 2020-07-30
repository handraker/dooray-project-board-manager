package com.toast.cloud.dpbm.application.model.issue;

import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.IssueTag;
import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class IssueDTO {

    private String issueId;
    private String parentIssueId;
    private String projectId;
    private String memberId;
    private int issueNo;
    private String title;
    private String moduleId;
    private String workingTypeId;
    private BigDecimal mandays;
    private String workflowId;
    private WorkflowTypeCode workflowTypeCode;
    private String milestoneId;
    private List<String> tagIdList;
    private ZonedDateTime updatedAt;

    public IssueDTO(Issue issue) {
        this.issueId = issue.getId();
        this.parentIssueId = issue.getParentIssueId();
        this.projectId = issue.getProjectId();
        this.memberId = issue.getMemberId();
        this.issueNo = issue.getIssueNo();
        this.title = issue.getTitle();
        this.moduleId = issue.getModuleId();
        this.workingTypeId = issue.getWorkingTypeId();
        this.mandays = issue.getMandays();
        this.workflowId = issue.getWorkflow().getWorkflowId();
        this.workflowTypeCode = issue.getWorkflow().getWorkflowTypeCode();
        this.milestoneId = issue.getMilestoneId();
        this.tagIdList = issue.getIssueTagList()
            .stream()
            .sorted(Comparator.comparing(IssueTag::getSortOrder))
            .map(IssueTag::getTagId)
            .collect(Collectors.toList());
        this.updatedAt = issue.getUpdatedAt();
    }

    @Builder
    public IssueDTO(@NonNull String issueId,
                    String parentIssueId,
                    @NonNull String projectId,
                    @NonNull String title,
                    int issueNo,
                    String memberId,
                    String moduleId,
                    String workingTypeId,
                    BigDecimal mandays,
                    @NonNull String workflowId,
                    @NonNull WorkflowTypeCode workflowTypeCode,
                    String milestoneId,
                    @NonNull List<String> tagIdList,
                    @NonNull ZonedDateTime updatedAt) {
        this.issueId = issueId;
        this.parentIssueId = parentIssueId;
        this.projectId = projectId;
        this.issueNo = issueNo;
        this.title = title;
        this.memberId = memberId;
        this.moduleId = moduleId;
        this.workingTypeId = workingTypeId;
        this.mandays = mandays;
        this.workflowId = workflowId;
        this.workflowTypeCode = workflowTypeCode;
        this.milestoneId = milestoneId;
        this.tagIdList = tagIdList;
        this.updatedAt = updatedAt;
    }

}
