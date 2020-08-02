package com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class DeleteIssueRequest {

    @Size(min = 1)
    @NotNull
    private List<String> issueIdList;

}
