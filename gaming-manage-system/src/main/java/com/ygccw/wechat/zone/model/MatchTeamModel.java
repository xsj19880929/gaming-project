package com.ygccw.wechat.zone.model;

import com.ygccw.wechat.common.zone.entity.MatchTeam;

import java.util.List;

/**
 * @author soldier
 */
public class MatchTeamModel extends MatchTeam {
    private List<MatchTeamMappingModel> matchTeamMappingModelList;

    public List<MatchTeamMappingModel> getMatchTeamMappingModelList() {
        return matchTeamMappingModelList;
    }

    public void setMatchTeamMappingModelList(List<MatchTeamMappingModel> matchTeamMappingModelList) {
        this.matchTeamMappingModelList = matchTeamMappingModelList;
    }
}
