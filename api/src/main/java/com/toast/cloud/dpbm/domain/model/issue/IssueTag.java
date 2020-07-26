package com.toast.cloud.dpbm.domain.model.issue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "dpbm_issue_tag")
@Entity
@IdClass(IssueTagId.class)
public class IssueTag implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    private Issue issue;
    @Id
    private String tagId;
    private Integer sortOrder;

}
