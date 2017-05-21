package com.ygccw.msite.mobile.game.model;

/**
 * @author soldier
 */
public class GameRequest {
    private Long matchZoneAreaId;
    private Long matchZoneYearId;
    private String matchStatusStr;

    public Long getMatchZoneAreaId() {
        return matchZoneAreaId;
    }

    public void setMatchZoneAreaId(Long matchZoneAreaId) {
        this.matchZoneAreaId = matchZoneAreaId;
    }

    public Long getMatchZoneYearId() {
        return matchZoneYearId;
    }

    public void setMatchZoneYearId(Long matchZoneYearId) {
        this.matchZoneYearId = matchZoneYearId;
    }

    public String getMatchStatusStr() {
        return matchStatusStr;
    }

    public void setMatchStatusStr(String matchStatusStr) {
        this.matchStatusStr = matchStatusStr;
    }
}
