package com.ygccw.website.pc.game.service;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneArea;
import com.ygccw.wechat.common.zone.entity.MatchZoneYear;
import com.ygccw.wechat.common.zone.enums.MatchStatus;
import com.ygccw.wechat.common.zone.service.MatchTeamMappingService;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import com.ygccw.wechat.common.zone.service.MatchZoneAreaService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneYearService;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Controller
public class GameWebService {
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private MatchZoneYearService matchZoneYearService;
    @Inject
    private MatchZoneAreaService matchZoneAreaService;
    @Inject
    private MatchTeamMappingService matchTeamMappingService;
    @Inject
    private MatchTeamService matchTeamService;


    public List<MatchZone> findMatchZoneNew(MatchZone matchZone, int offset, int fetchSize) {
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public List<MatchZone> findMatchZoneTop(MatchZone matchZone, int offset, int fetchSize) {
        matchZone.setSortName("visitCount");
        matchZone.setSortIfDesc(true);
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public List<MatchZoneYear> listMatchZoneYear() {
        return matchZoneYearService.listAll();
    }

    public List<MatchZoneArea> listMatchZoneArea() {
        return matchZoneAreaService.listAll();
    }

    public List<MatchStatus> listMatchStatus() {
        List<MatchStatus> matchStatusList = new ArrayList<>();
        for (MatchStatus matchStatus : MatchStatus.values()) {
            matchStatusList.add(matchStatus);
        }
        return matchStatusList;
    }

    public MatchZone findById(Long id) {
        return matchZoneService.findById(id);
    }

    public List<MatchTeam> listMatchTeam(Long matchZoneId) {
        List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingService.listByMatchZoneId(matchZoneId);
        List<MatchTeam> matchTeamList = new ArrayList<>();
        for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
            MatchTeam matchTeam = matchTeamService.findById(matchTeamMapping.getMatchTeamId());
            matchTeamList.add(matchTeam);
        }
        return matchTeamList;
    }


}
