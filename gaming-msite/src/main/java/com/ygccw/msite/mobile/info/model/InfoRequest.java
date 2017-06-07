package com.ygccw.msite.mobile.info.model;

import com.ygccw.wechat.common.info.entity.Info;

/**
 * @author soldier
 */
public class InfoRequest extends Info {
    private String templateName;
    private Long tagId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
