package com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo;

import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.code.WorkflowTypeCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CreateIssueRequest {

    @NotNull
    @Valid
    private List<Issue> issueList;

    @Getter
    @Setter
    public static class Issue {

        @NotNull
        private String issueId;
        private String parentIssueId;
        @NotNull
        private String projectId;
        @NotNull
        private String title;
        private int issueNo;
        private String memberId;
        private String moduleId;
        private String workingTypeId;
        private BigDecimal mandays;
        @NotNull
        private String workflowId;
        @NotNull
        private WorkflowTypeCode workflowTypeCode;
        private String milestoneId;
        @NotNull
        private List<String> tagIdList;
        @NotNull
        private OffsetDateTime updatedAt;

    }

    public List<IssueDTO> getIssueDTOList() {
        return issueList.stream()
            .map(issue -> IssueDTO.builder()
                .issueId(issue.issueId)
                .parentIssueId(issue.parentIssueId)
                .projectId(issue.projectId)
                .title(issue.title)
                .issueNo(issue.getIssueNo())
                .memberId(issue.memberId)
                .moduleId(issue.moduleId)
                .workingTypeId(issue.workingTypeId)
                .mandays(issue.mandays)
                .workflowId(issue.workflowId)
                .workflowTypeCode(issue.workflowTypeCode)
                .milestoneId(issue.milestoneId)
                .tagIdList(issue.tagIdList)
                .updatedAt(issue.updatedAt.toZonedDateTime())
                .build())
            .collect(Collectors.toList());
    }

}
