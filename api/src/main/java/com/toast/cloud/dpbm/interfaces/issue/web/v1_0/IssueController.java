package com.toast.cloud.dpbm.interfaces.issue.web.v1_0;

import com.toast.cloud.dpbm.application.model.issue.IssueDTO;
import com.toast.cloud.dpbm.application.service.issue.IssueAppService;
import com.toast.cloud.dpbm.domain.model.issue.IssuePredicate;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.CreateIssueRequest;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.GetIssueRequest;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.ModifyIssueProgressRequest;
import com.toast.cloud.dpbm.interfaces.issue.web.v1_0.vo.StartTimerRequest;
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
    public void createIssue(@Valid @RequestBody CreateIssueRequest request) {
        issueAppService.create(request.getIssueDTOList());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @PostMapping("/dpbm/issue/stop-timer")
    public void stopTimer() {
        issueAppService.stopTimer();
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @PostMapping("/dpbm/issue/{issueId}/start-timer")
    public void startTimer(@PathVariable("issueId") String issueId, @Valid @RequestBody StartTimerRequest request) {
        issueAppService.startTimer(issueId, request.getModuleName());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @DeleteMapping("/dpbm/issue/{issueId}")
    public void deleteIssue(@PathVariable("issueId") String issueId) {
        issueAppService.deleteIssue(issueId);
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @DeleteMapping("/dpbm/issue/{issueId}/child-issue")
    public void deleteChildIssue(@PathVariable("issueId") String issueId) {
        issueAppService.deleteChildIssue(issueId);
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @PutMapping("/dpbm/issue/{issueId}/progress")
    public void modifyIssueProgress(@PathVariable("issueId") String issueId,
                                    @Valid @RequestBody ModifyIssueProgressRequest request) {
        issueAppService.modifyIssueProgress(issueId, request.getIssueProgressDTO());
    }

    @CrossOrigin("https://nhnent.dooray.com")
    @GetMapping("/dpbm/issue")
    public List<IssueDTO> getIssues(@Valid @ModelAttribute GetIssueRequest request) {
        return issueAppService.getIssues(request.getMemberId(), IssuePredicate.builder()
            .projectId(request.getProjectId())
            .memberId(request.getMemberId())
            .from(request.getFrom().atStartOfDay(ZoneId.systemDefault()))
            .to(request.getTo().atStartOfDay(ZoneId.systemDefault()))
            .workflowTypeCode(request.getWorkflowTypeCode())
            .build());
    }

}
