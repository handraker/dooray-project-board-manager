package com.toast.cloud.dpbm.interfaces.board.web.v1_0.vo;

import com.toast.cloud.dpbm.interfaces.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetParentIssueBoardRequest extends PageableRequest {

    private String moduleId;
    private String milestoneId;

}
