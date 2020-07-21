package com.toast.cloud.dpbm.application.service.board;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoard;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoardItem;
import com.toast.cloud.dpbm.domain.model.issue.*;
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

    public ParentIssueBoard getParentIssueBoard(String projectId, String moduleId) {
        Iterable<ParentIssue> iterable = parentIssueRepository.findAll(ParentIssuePredicate.builder()
                                                                           .projectId(projectId)
                                                                           .moduleId(moduleId)
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
