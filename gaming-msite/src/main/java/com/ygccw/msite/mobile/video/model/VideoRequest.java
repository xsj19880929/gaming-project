package com.ygccw.msite.mobile.video.model;

import com.ygccw.wechat.common.info.enums.InfoZoneType;

/**
 * @author soldier
 */
public class VideoRequest {
    private String infoVideoTypeStr;
    private String infoZoneTypeStr;
    private Long zoneId;
    private String sortName;
    private Boolean sortIfDesc;
    private String templateName;
    private InfoZoneType infoZoneType;
    private Long tagId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public InfoZoneType getInfoZoneType() {
        return infoZoneType;
    }

    public void setInfoZoneType(InfoZoneType infoZoneType) {
        this.infoZoneType = infoZoneType;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getInfoVideoTypeStr() {
        return infoVideoTypeStr;
    }

    public void setInfoVideoTypeStr(String infoVideoTypeStr) {
        this.infoVideoTypeStr = infoVideoTypeStr;
    }

    public String getInfoZoneTypeStr() {
        return infoZoneTypeStr;
    }

    public void setInfoZoneTypeStr(String infoZoneTypeStr) {
        this.infoZoneTypeStr = infoZoneTypeStr;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Boolean getSortIfDesc() {
        return sortIfDesc;
    }

    public void setSortIfDesc(Boolean sortIfDesc) {
        this.sortIfDesc = sortIfDesc;
    }
}
