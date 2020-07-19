package com.toast.cloud.dpbm.interfaces;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PageableRequest {

    @Min(1)
    @NotNull
    private Integer page;
    @Min(1)
    @Max(1000)
    @NotNull
    private Integer itemsPerPage;

}
