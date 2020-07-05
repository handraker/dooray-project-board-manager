package com.toast.cloud.dpbm.application.hook;

import com.toast.cloud.common.utils.ObjectMapperUtils;
import com.toast.cloud.dpbm.application.hook.vo.PostCreatedRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class HookAppService {

    public void process(String webhookType, Map<String, Object> payload) {
        switch (webhookType) {
            case "postCreated":
                PostCreatedRequest request = ObjectMapperUtils.convert(payload, PostCreatedRequest.class);
                return;
            case "postTagChanged":
                return;
        }
    }

}
