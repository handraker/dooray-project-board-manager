package com.toast.cloud.dpbm.application.service.board.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class ParentIssueBoardCriteria {

    private Pageable pageable;
    private String moduleId;
    private String milestoneId;

}
