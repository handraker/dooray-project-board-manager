package com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StartTimerRequest {

    @NotNull
    private String moduleName;

}
