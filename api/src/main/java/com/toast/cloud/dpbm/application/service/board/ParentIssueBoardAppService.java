package com.toast.cloud.dpbm.application.service.board;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoard;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoardItem;
import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.IssuePredicate;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ParentIssueBoardAppService {

    private final IssueRepository issueRepository;

    public ParentIssueBoard getParentIssueBoard(String projectId, String milestoneId, String moduleId, boolean showInProgress) {
        Iterable<Issue> iterable = issueRepository.findParentIssue(IssuePredicate.builder()
                                                                       .projectId(projectId)
                                                                       .moduleId(moduleId)
                                                                       .milestoneId(milestoneId)
                                                                       .showInProgress(showInProgress)
                                                                       .build());
        List<ParentIssueBoardItem> items = StreamSupport.stream(iterable.spliterator(), false)
            .map(parentIssue -> {
                Predicate predicate = IssuePredicate.builder()
                    .parentIssueId(parentIssue.getId())
                    .build();
                return new ParentIssueBoardItem(parentIssue,
                                                issueRepository.getMandaysIssueWorkflowStatistics(predicate),
                                                issueRepository.getCountIssueWorkflowStatistics(predicate));
            })
            .sorted()
            .collect(Collectors.toList());
        return new ParentIssueBoard(items);
    }

}
