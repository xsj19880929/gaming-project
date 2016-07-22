package com.ygccw.website.pc.index.model;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchZone;

import java.util.List;

/**
 * @author soldier
 */
public class MatchZoneWeb extends MatchZone {
    private List<MatchTeam> matchTeamList;

    public List<MatchTeam> getMatchTeamList() {
        return matchTeamList;
    }

    public void setMatchTeamList(List<MatchTeam> matchTeamList) {
        this.matchTeamList = matchTeamList;
    }
}
