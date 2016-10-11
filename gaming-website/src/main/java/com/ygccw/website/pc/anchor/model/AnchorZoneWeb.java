package com.ygccw.website.pc.anchor.model;

import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;

import java.util.List;

/**
 * @author soldier
 */
public class AnchorZoneWeb extends AnchorZone {
    private List<MatchZone> matchZoneList;

    public List<MatchZone> getMatchZoneList() {
        return matchZoneList;
    }

    public void setMatchZoneList(List<MatchZone> matchZoneList) {
        this.matchZoneList = matchZoneList;
    }
}
