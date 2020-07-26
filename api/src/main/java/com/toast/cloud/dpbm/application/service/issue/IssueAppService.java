package com.toast.cloud.dpbm.application.service.issue;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.Issue;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IssueAppService {

    private final IssueRepository issueRepository;

    public List<IssueDTO> getIssues(Predicate predicate) {
        return issueRepository.findByPredicate(predicate)
            .stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void create(List<IssueDTO> issueDTOList) {
        List<Issue> issueList = issueDTOList.stream()
            .map(issueDTO -> {
                issueRepository.findById(issueDTO.getIssueId()).ifPresent(issueRepository::delete);
                return Issue.factory()
                    .id(issueDTO.getIssueId())
                    .parentIssueId(issueDTO.getParentIssueId())
                    .projectId(issueDTO.getProjectId())
                    .title(issueDTO.getTitle())
                    .memberId(issueDTO.getMemberId())
                    .moduleId(issueDTO.getModuleId())
                    .workingTypeId(issueDTO.getWorkingTypeId())
                    .mandays(issueDTO.getMandays())
                    .workflowId(issueDTO.getWorkflowId())
                    .workflowTypeCode(issueDTO.getWorkflowTypeCode())
                    .milestoneId(issueDTO.getMilestoneId())
                    .tagIdList(issueDTO.getTagIdList())
                    .updatedAt(issueDTO.getUpdatedAt())
                    .newInstance();
            })
            .collect(Collectors.toList());

        issueRepository.saveAll(issueList);
    }

}
