package com.toast.cloud.dpbm.application.model.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ParentIssueBoard {

    private List<ParentIssueBoardItem> items;

}
