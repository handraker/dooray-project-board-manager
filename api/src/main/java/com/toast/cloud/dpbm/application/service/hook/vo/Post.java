package com.toast.cloud.dpbm.application.service.hook.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class Post {

    private String id;
    private Integer number;
    private String subject;
    private ParentPost parent;
    private List<Tag> tags;

}
