package com.toast.cloud.dpbm.interfaces.board.web.v1_0.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetParentIssueBoardRequest {

    private String moduleId;
    private String milestoneId;
    private Boolean showInProgress = Boolean.FALSE;

}
