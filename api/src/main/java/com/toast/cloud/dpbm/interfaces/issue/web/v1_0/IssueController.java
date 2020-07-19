package com.toast.cloud.dpbm.interfaces.issue.web.v1_0;

import com.toast.cloud.dpbm.application.service.issue.IssueAppService;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.CreateIssueRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class IssueController {

    private final IssueAppService issueAppService;

    @CrossOrigin("https://nhnent.dooray.com")
    @PostMapping("/dpbm/issue")
    public void createParentIssue(@Valid @RequestBody CreateIssueRequest request) {
        issueAppService.create(request.getIssueDTO());
    }

}
