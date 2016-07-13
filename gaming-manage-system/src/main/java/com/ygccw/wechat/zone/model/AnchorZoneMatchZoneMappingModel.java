package com.ygccw.wechat.zone.model;

import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;

/**
 * @author soldier
 */
public class AnchorZoneMatchZoneMappingModel extends AnchorZoneMatchZoneMapping {
    private Boolean checked;
    private String matchZoneName;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getMatchZoneName() {
        return matchZoneName;
    }

    public void setMatchZoneName(String matchZoneName) {
        this.matchZoneName = matchZoneName;
    }
}
