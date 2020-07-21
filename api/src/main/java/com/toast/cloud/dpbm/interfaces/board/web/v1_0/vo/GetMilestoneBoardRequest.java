package com.toast.cloud.dpbm.interfaces.board.web.v1_0.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetMilestoneBoardRequest {

    @NotNull
    private String moduleId;

}
