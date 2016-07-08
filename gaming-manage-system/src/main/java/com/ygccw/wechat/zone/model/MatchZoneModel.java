package com.ygccw.wechat.zone.model;

import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;
import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;

import java.util.List;

/**
 * @author soldier
 */
public class MatchZoneModel extends MatchZone {
    private List<MatchZoneCalendar> matchZoneCalendarList;
    private List<MatchZoneBonus> matchZoneBonusList;

    public List<MatchZoneCalendar> getMatchZoneCalendarList() {
        return matchZoneCalendarList;
    }

    public void setMatchZoneCalendarList(List<MatchZoneCalendar> matchZoneCalendarList) {
        this.matchZoneCalendarList = matchZoneCalendarList;
    }

    public List<MatchZoneBonus> getMatchZoneBonusList() {
        return matchZoneBonusList;
    }

    public void setMatchZoneBonusList(List<MatchZoneBonus> matchZoneBonusList) {
        this.matchZoneBonusList = matchZoneBonusList;
    }
}
