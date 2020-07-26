package com.toast.cloud.dpbm.interfaces.issue.web.v1_0;

import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.application.service.issue.IssueAppService;
import com.toast.cloud.dpbm.domain.model.issue.IssuePredicate;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.CreateIssueRequest;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.GetIssueRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.List;

@AllArgsConstructor
@RestController
public class IssueController {

    private final IssueAppService issueAppService;

    @CrossOrigin("https://nhnent.dooray.com")
    @PostMapping("/dpbm/issue")
    public void createParentIssue(@Valid @RequestBody CreateIssueRequest request) {
        issueAppService.create(request.getIssueDTOList());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/issue")
    public List<IssueDTO> getIssues(@Valid @ModelAttribute GetIssueRequest request) {
        return issueAppService.getIssues(IssuePredicate.builder()
                                             .memberId(request.getMemberId())
                                             .from(request.getFrom().atStartOfDay(ZoneId.systemDefault()))
                                             .to(request.getTo().atStartOfDay(ZoneId.systemDefault()))
                                             .build());
    }

}
