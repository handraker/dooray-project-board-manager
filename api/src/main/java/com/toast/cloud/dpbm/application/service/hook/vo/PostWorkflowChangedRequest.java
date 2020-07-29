package com.toast.cloud.dpbm.application.service.hook.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PostWorkflowChangedRequest {

    private Post post;
    private Workflow workflow;

    @Getter
    public static class Post {

        private String id;

    }

    @Getter
    public static class Workflow {

        private String id;
        @JsonProperty("class")
        private String clazz;

    }

}
