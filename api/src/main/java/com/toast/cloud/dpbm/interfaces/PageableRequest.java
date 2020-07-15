package com.toast.cloud.dpbm.interfaces;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageableRequest {

    private int page;
    private int itemsPerPage;

    @ApiModelProperty(hidden = true, readOnly = true)
    public Pageable getPageable() {
        return PageRequest.of(page, itemsPerPage);
    }

}
