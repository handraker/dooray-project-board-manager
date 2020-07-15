package com.toast.cloud.dpbm.interfaces.board.web.v1_0;

import com.toast.cloud.dpbm.application.model.board.ParentIssueBoard;
import com.toast.cloud.dpbm.application.service.board.ParentIssueBoardAppService;
import com.toast.cloud.dpbm.application.service.board.vo.ParentIssueBoardCriteria;
import com.toast.cloud.dpbm.interfaces.board.web.v1_0.vo.GetParentIssueBoardRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class BoardController {

    private final ParentIssueBoardAppService parentIssueBoardAppService;

    @GetMapping("/projects/{projectId}/boards/parent-issue")
    public ParentIssueBoard getParentIssueBoard(@PathVariable("projectId") String projectId,
                                                @Valid @ModelAttribute GetParentIssueBoardRequest request) {
        ParentIssueBoardCriteria criteria = new ParentIssueBoardCriteria(request.getPageable(),
                                                                         request.getModuleId(),
                                                                         request.getMilestoneId());
        return parentIssueBoardAppService.getParentIssueBoard(projectId, criteria);
    }

}
