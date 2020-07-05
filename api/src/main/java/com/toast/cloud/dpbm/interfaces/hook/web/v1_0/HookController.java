package com.toast.cloud.dpbm.interfaces.hook.web.v1_0;

import com.toast.cloud.dpbm.application.hook.HookAppService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class HookController {

    private final HookAppService hookAppService;

    @PostMapping("/hooks/process")
    public void process(@RequestBody Map<String, Object> request) {
        hookAppService.process((String) request.get("webhookType"), request);
    }

}
