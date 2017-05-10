package com.ygccw.msite.mobile.index.model;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchZone;

import java.util.List;

/**
 * @author soldier
 */
public class MatchTeamWeb extends MatchTeam {
    private List<MatchZone> matchZoneList;

    public List<MatchZone> getMatchZoneList() {
        return matchZoneList;
    }

    public void setMatchZoneList(List<MatchZone> matchZoneList) {
        this.matchZoneList = matchZoneList;
    }
}
