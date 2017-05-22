package com.ygccw.msite.mobile.game.model;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;

/**
 * @author soldier
 */
public class MatchZoneCalendarWeb extends MatchZoneCalendar {
    private MatchTeam matchTeamOne;
    private MatchTeam matchTeamTwo;

    public MatchTeam getMatchTeamOne() {
        return matchTeamOne;
    }

    public void setMatchTeamOne(MatchTeam matchTeamOne) {
        this.matchTeamOne = matchTeamOne;
    }

    public MatchTeam getMatchTeamTwo() {
        return matchTeamTwo;
    }

    public void setMatchTeamTwo(MatchTeam matchTeamTwo) {
        this.matchTeamTwo = matchTeamTwo;
    }
}
