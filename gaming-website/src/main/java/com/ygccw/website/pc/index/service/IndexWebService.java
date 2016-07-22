package com.ygccw.website.pc.index.service;

import com.ygccw.website.pc.index.model.MatchZoneWeb;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.MatchTeamMappingService;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Controller
public class IndexWebService {
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private RecommendMappingService recommendMappingService;
    @Inject
    private MatchTeamMappingService matchTeamMappingService;
    @Inject
    private MatchTeamService matchTeamService;

    public List<MatchZoneWeb> findRecommendMatchZone() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByRecommendIdAndType(1L, RecommendType.matchZone, 0, 4);
        List<MatchZoneWeb> matchZoneWebList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            MatchZoneWeb matchZoneWeb = new MatchZoneWeb();
            MatchZone matchZone = matchZoneService.findById(recommendMapping.getEntityId());
            BeanUtils.copyProperties(matchZone, matchZoneWeb);
            List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingService.listByMatchZoneId(recommendMapping.getEntityId());
            List<MatchTeam> matchTeamList = new ArrayList<>();
            for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
                MatchTeam matchTeam = matchTeamService.findById(matchTeamMapping.getMatchTeamId());
                matchTeamList.add(matchTeam);
            }
            matchZoneWeb.setMatchTeamList(matchTeamList);
            matchZoneWebList.add(matchZoneWeb);
        }
        return matchZoneWebList;
    }
}
