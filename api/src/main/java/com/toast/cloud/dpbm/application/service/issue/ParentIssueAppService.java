package com.toast.cloud.dpbm.application.service.issue;

import com.toast.cloud.dpbm.application.model.issue.ParentIssueDTO;
import com.toast.cloud.dpbm.domain.model.issue.IssueRepository;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssue;
import com.toast.cloud.dpbm.domain.model.issue.ParentIssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ParentIssueAppService {

    private final ParentIssueRepository parentIssueRepository;
    private final IssueRepository issueRepository;

    @Transactional
    public void create(ParentIssueDTO parentIssueDTO) {
        ParentIssue parentIssue = parentIssueRepository.findById(parentIssueDTO.getParentIssueId())
            .map(inner -> {
                inner.setTitle(parentIssueDTO.getTitle());
                inner.setIssueNo(parentIssueDTO.getIssueNo());
                inner.setModuleId(parentIssueDTO.getModuleId());
                inner.setMilestoneId(parentIssueDTO.getMilestoneId());
                return inner;
            })
            .orElseGet(() -> ParentIssue.factory()
                .id(parentIssueDTO.getParentIssueId())
                .projectId(parentIssueDTO.getProjectId())
                .title(parentIssueDTO.getTitle())
                .issueNo(parentIssueDTO.getIssueNo())
                .moduleId(parentIssueDTO.getModuleId())
                .devStatusCode(parentIssueDTO.getDevStatusCode())
                .devStartDate(parentIssueDTO.getDevStartDate())
                .devEndDate(parentIssueDTO.getDevEndDate())
                .deployStatusCode(parentIssueDTO.getDeployStatusCode())
                .deployStartDate(parentIssueDTO.getDeployStartDate())
                .deployEndDate(parentIssueDTO.getDeployEndDate())
                .milestoneId(parentIssueDTO.getMilestoneId())
                .newInstance());
        parentIssueRepository.save(parentIssue);
    }

    @Transactional
    public void modify(ParentIssueDTO parentIssueDTO) {
        parentIssueRepository.findById(parentIssueDTO.getParentIssueId()).ifPresent(parentIssue -> {
            parentIssue.modify(parentIssueDTO.getTitle(),
                               parentIssueDTO.getIssueNo(),
                               parentIssueDTO.getModuleId(),
                               parentIssueDTO.getDevStatusCode(),
                               parentIssueDTO.getDevStartDate(),
                               parentIssueDTO.getDevEndDate(),
                               parentIssueDTO.getDeployStatusCode(),
                               parentIssueDTO.getDeployStartDate(),
                               parentIssueDTO.getDeployEndDate(),
                               parentIssueDTO.getMilestoneId());
            parentIssueRepository.save(parentIssue);
        });
    }

    @Transactional
    public void deleteChildIssue(String parentIssueId) {
        issueRepository.deleteByParentIssue(parentIssueId);
    }

}
