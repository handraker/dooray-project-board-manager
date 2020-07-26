package com.toast.cloud.dpbm.domain.model.issue;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class IssueTagId implements Serializable {

    private Issue issue;
    private String tagId;

}
