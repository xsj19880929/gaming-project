package com.ygccw.website.pc.info.model;

import com.ygccw.wechat.common.info.entity.Info;

import java.util.List;

/**
 * @author soldier
 */
public class InfoWeb extends Info {
    private List<TagMappingWeb> tagMappingWebList;

    public List<TagMappingWeb> getTagMappingWebList() {
        return tagMappingWebList;
    }

    public void setTagMappingWebList(List<TagMappingWeb> tagMappingWebList) {
        this.tagMappingWebList = tagMappingWebList;
    }
}
