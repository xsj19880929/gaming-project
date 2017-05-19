package com.ygccw.msite.mobile.video.model;

/**
 * @author soldier
 */
public class VideoRequest {
    private String infoVideoTypeStr;
    private String infoZoneTypeStr;
    private Long zoneId;
    private String sortName;
    private Boolean sortIfDesc;

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
