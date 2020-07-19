package com.toast.cloud.dpbm.application.service.board;

import com.toast.cloud.dpbm.application.model.board.ParentIssueBoard;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoardItem;
import com.toast.cloud.dpbm.application.service.board.vo.ParentIssueBoardCriteria;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssuePredicate;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ParentIssueBoardAppService {

    private final IssueRepository issueRepository;
    private final ParentIssueRepository parentIssueRepository;

    public ParentIssueBoard getParentIssueBoard(String projectId, ParentIssueBoardCriteria criteria) {
        Iterable<ParentIssue> iterable = parentIssueRepository.findAll(ParentIssuePredicate.where(projectId,
                                                                                                  criteria.getModuleId(),
                                                                                                  criteria.getMilestoneId()));
        List<ParentIssueBoardItem> items = StreamSupport.stream(iterable.spliterator(), false)
            .map(parentIssue -> new ParentIssueBoardItem(parentIssue,
                                                         issueRepository.getMandaysIssueWorkflowStatistics(parentIssue.getId()),
                                                         issueRepository.getCountIssueWorkflowStatistics(parentIssue.getId())))
            .sorted()
            .collect(Collectors.toList());
        return new ParentIssueBoard(items);
    }

}
