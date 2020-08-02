package com.toast.cloud.dpbm.application.service.issue;

import com.querydsl.core.types.Predicate;
import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.application.model.issue.IssueProgressDTO;
import com.toast.cloud.dpbm.domain.model.issue.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IssueAppService {

    private final IssueRepository issueRepository;
    private final IssueProgressRepository issueProgressRepository;

    public List<IssueDTO> getIssues(Predicate predicate) {
        return issueRepository.findByPredicate(predicate)
            .stream()
            .map(IssueDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteIssue(String issueId) {
        issueRepository.deleteByIssueId(issueId);
    }

    @Transactional
    public void deleteChildIssue(String parentIssueId) {
        issueRepository.deleteByParentIssue(parentIssueId);
    }

    @Transactional
    public void modifyIssueProgress(String issueId, IssueProgressDTO issueProgressDTO) {
        IssueProgress newIssueProgress = issueProgressRepository.findById(issueId)
            .map(issueProgress -> issueProgress.modify(issueProgressDTO))
            .orElseGet(() -> new IssueProgress(issueId, issueProgressDTO));
        issueProgressRepository.save(newIssueProgress);
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
                    .issueNo(issueDTO.getIssueNo())
                    .memberId(issueDTO.getMemberId())
                    .moduleId(issueDTO.getModuleId())
                    .workingTypeId(issueDTO.getWorkingTypeId())
                    .mandays(issueDTO.getMandays())
                    .workflow(new Workflow(issueDTO.getWorkflowId(), issueDTO.getWorkflowTypeCode()))
                    .milestoneId(issueDTO.getMilestoneId())
                    .tagIdList(issueDTO.getTagIdList())
                    .updatedAt(issueDTO.getUpdatedAt())
                    .newInstance();
            })
            .collect(Collectors.toList());

        issueRepository.saveAll(issueList);
    }

}
