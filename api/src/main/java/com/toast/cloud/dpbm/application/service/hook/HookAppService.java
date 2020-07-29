package com.toast.cloud.dpbm.application.service.hook;

import com.toast.cloud.common.utils.ObjectMapperUtils;
import com.toast.cloud.dpbm.application.service.hook.vo.PostCreatedRequest;
import com.toast.cloud.dpbm.application.service.hook.vo.PostWorkflowChangedRequest;
import com.toast.cloud.dpbm.domain.model.issue.Workflow;
import com.toast.cloud.dpbm.domain.service.issue.IssueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class HookAppService {

    private final IssueService issueService;

    public void process(String webhookType, Map<String, Object> payload) {
        switch (webhookType) {
            case "postCreated":
                PostCreatedRequest postCreatedRequest = ObjectMapperUtils.convert(payload, PostCreatedRequest.class);
                return;
            case "postWorkflowChanged":
                PostWorkflowChangedRequest postWorkflowChangedRequest =
                    ObjectMapperUtils.convert(payload, PostWorkflowChangedRequest.class);
                issueService.changeWorkflow(postWorkflowChangedRequest.getPost().getId(),
                                            new Workflow(postWorkflowChangedRequest.getWorkflow().getId(),
                                                         postWorkflowChangedRequest.getWorkflow().getClazz()));
                return;
            case "postTagChanged":
                return;
        }
    }

}
