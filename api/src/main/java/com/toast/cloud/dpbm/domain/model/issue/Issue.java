package com.toast.cloud.dpbm.domain.model.issue;

import com.toast.cloud.common.jpa.enitty.AbstractBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dpbm_issue")
@Entity
public class Issue extends AbstractBaseEntity<String> {

    private String parentIssueId;
    private String projectId;
    private int issueNo;
    private String title;
    private String memberId;
    private String moduleId;
    private String workingTypeId;
    private BigDecimal mandays;
    @Setter
    private Workflow workflow;
    private String milestoneId;
    private ZonedDateTime updatedAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private IssueProgress progress;
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueTag> issueTagList;

    @Builder(builderMethodName = "factory", buildMethodName = "newInstance")
    private Issue(String id,
                  String parentIssueId,
                  @NonNull String projectId,
                  @NonNull String title,
                  int issueNo,
                  String memberId,
                  String moduleId,
                  String workingTypeId,
                  BigDecimal mandays,
                  @NonNull Workflow workflow,
                  String milestoneId,
                  @NonNull ZonedDateTime updatedAt,
                  @NonNull List<String> tagIdList) {
        generateId(id);
        this.parentIssueId = parentIssueId;
        this.projectId = projectId;
        this.title = title;
        this.issueNo = issueNo;
        this.memberId = memberId;
        this.moduleId = moduleId;
        this.workingTypeId = workingTypeId;
        this.mandays = mandays;
        this.workflow = workflow;
        this.milestoneId = milestoneId;
        this.updatedAt = updatedAt;
        this.issueTagList = new ArrayList<>();
        for (int i = 0; i < tagIdList.size(); i++) {
            issueTagList.add(new IssueTag(this, tagIdList.get(i), i));
        }
    }

    public void modifyByCodeFreeze(LocalDate codeFreezeDate) {
        if (progress == null) {
            progress = new IssueProgress(getId(), codeFreezeDate);
        } else {
            progress.modifyByCodeFreeze(codeFreezeDate);
        }
    }

    public String getClockifyDescription() {
        return String.format("%d %s", issueNo, title);
    }

}
