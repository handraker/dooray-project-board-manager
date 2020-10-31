package com.toast.cloud.dpbm.interfaces.board.web.v1_0;

import com.toast.cloud.dpbm.application.model.board.MilestoneBoard;
import com.toast.cloud.dpbm.application.model.board.ParentIssueBoard;
import com.toast.cloud.dpbm.application.service.board.MilestoneBoardAppService;
import com.toast.cloud.dpbm.application.service.board.ParentIssueBoardAppService;
import com.toast.cloud.dpbm.interfaces.board.web.v1_0.vo.GetMilestoneBoardRequest;
import com.toast.cloud.dpbm.interfaces.board.web.v1_0.vo.GetParentIssueBoardRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class BoardController {

    private final ParentIssueBoardAppService parentIssueBoardAppService;
    private final MilestoneBoardAppService milestoneBoardAppService;

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/projects/{projectId}/boards/parent-issue")
    public ParentIssueBoard getParentIssueBoard(@PathVariable("projectId") String projectId,
                                                @Valid @ModelAttribute GetParentIssueBoardRequest request) {
        return parentIssueBoardAppService.getParentIssueBoard(projectId,
                                                              request.getMilestoneId(),
                                                              request.getModuleId(),
                                                              request.getShowInProgress(),
                                                              request.getWithStatistics());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/projects/{projectId}/boards/milestone")
    public MilestoneBoard getMilestoneBoard(@PathVariable("projectId") String projectId,
                                            @Valid @ModelAttribute GetMilestoneBoardRequest request) {
        return milestoneBoardAppService.getMilestoneBoard(projectId, request.getMilestoneId(), request.getModuleId());
    }

}
