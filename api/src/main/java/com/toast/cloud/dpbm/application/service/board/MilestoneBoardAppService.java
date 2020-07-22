package com.toast.cloud.dpbm.application.service.board;

import com.toast.cloud.dpbm.application.model.board.MilestoneBoard;
import com.toast.cloud.dpbm.domain.model.issue.IssuePredicate;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MilestoneBoardAppService {

    private final IssueRepository issueRepository;

    public MilestoneBoard getMilestoneBoard(String projectId, String milestoneId, String moduleId) {
        return new MilestoneBoard(issueRepository.getMandaysIssueWorkflowStatistics(IssuePredicate.builder()
                                                                                        .milestoneId(milestoneId)
                                                                                        .projectId(projectId)
                                                                                        .moduleId(moduleId)
                                                                                        .build()),
                                  issueRepository.getCountIssueWorkflowStatistics(IssuePredicate.builder()
                                                                                      .milestoneId(milestoneId)
                                                                                      .projectId(projectId)
                                                                                      .moduleId(moduleId)
                                                                                      .build()));
    }

}
