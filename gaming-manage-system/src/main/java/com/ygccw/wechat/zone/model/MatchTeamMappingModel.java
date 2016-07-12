package com.ygccw.wechat.zone.model;

import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;

/**
 * @author soldier
 */
public class MatchTeamMappingModel extends MatchTeamMapping {
    private Boolean checked;
    private String matchZoneName;

    public String getMatchZoneName() {
        return matchZoneName;
    }

    public void setMatchZoneName(String matchZoneName) {
        this.matchZoneName = matchZoneName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
