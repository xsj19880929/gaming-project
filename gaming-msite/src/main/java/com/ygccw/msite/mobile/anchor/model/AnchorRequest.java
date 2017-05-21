package com.ygccw.msite.mobile.anchor.model;

/**
 * @author soldier
 */
public class AnchorRequest {
    private Long platformId;
    private String sortName;
    private Boolean sortIfDesc;

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
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
