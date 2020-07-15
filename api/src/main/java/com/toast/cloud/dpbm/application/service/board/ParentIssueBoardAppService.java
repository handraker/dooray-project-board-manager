package com.toast.cloud.dpbm.application.service.board;

import com.toast.cloud.dpbm.application.model.board.ParentIssueBoard;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoardItem;
import com.toast.cloud.dpbm.application.service.board.vo.ParentIssueBoardCriteria;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssuePredicate;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ParentIssueBoardAppService {

    private final IssueRepository issueRepository;
    private final ParentIssueRepository parentIssueRepository;

    public ParentIssueBoard getParentIssueBoard(String projectId, ParentIssueBoardCriteria criteria) {
        Page<ParentIssue> parentIssues = parentIssueRepository.findAll(ParentIssuePredicate.where(projectId,
                                                                                                  criteria.getModuleId(),
                                                                                                  criteria.getMilestoneId()),
                                                                       criteria.getPageable());
        List<ParentIssueBoardItem> items = parentIssues.stream()
            .map(parentIssue -> new ParentIssueBoardItem(parentIssue, issueRepository.getIssueWorkflowStatistics(parentIssue.getId())))
            .collect(Collectors.toList());
        return new ParentIssueBoard(items, parentIssues.getTotalElements());
    }

}
