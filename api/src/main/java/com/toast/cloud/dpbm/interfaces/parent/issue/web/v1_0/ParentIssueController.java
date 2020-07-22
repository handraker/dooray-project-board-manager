package com.toast.cloud.dpbm.interfaces.parent.issue.web.v1_0;

import com.toast.cloud.dpbm.application.service.issue.ParentIssueAppService;
import com.toast.cloud.dpbm.interfaces.parent.issue.web.v1_0.vo.CreateParentIssueRequest;
import com.toast.cloud.dpbm.interfaces.parent.issue.web.v1_0.vo.ModifyParentIssueRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class ParentIssueController {

    private final ParentIssueAppService parentIssueAppService;

    @CrossOrigin("https://nhnent.dooray.com")
    @PostMapping("/dpbm/parent-issue")
    public void createParentIssue(@Valid @RequestBody CreateParentIssueRequest request) {
        parentIssueAppService.create(request.getParentIssueDTO());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @PutMapping("/dpbm/parent-issue")
    public void modifyParentIssue(@Valid @RequestBody ModifyParentIssueRequest request) {
        parentIssueAppService.modify(request.getParentIssueDTO());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @DeleteMapping("/dpbm/parent-issue/{parentIssueId}/issue")
    public void deleteChildIssue(@PathVariable("parentIssueId") String parentIssueId) {
        parentIssueAppService.deleteChildIssue(parentIssueId);
    }

}
