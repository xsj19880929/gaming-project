package com.ygccw.website.pc.index.service;

import com.ygccw.website.pc.index.model.MatchTeamWeb;
import com.ygccw.website.pc.index.model.MatchZoneWeb;
import com.ygccw.wechat.common.advertising.entity.Advertising;
import com.ygccw.wechat.common.advertising.service.AdvertisingService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendLocal;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
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
    @Inject
    private AdvertisingService advertisingService;
    @Inject
    private InfoService infoService;
    @Inject
    private AnchorZoneService anchorZoneService;

    public List<MatchZoneWeb> findRecommendMatchZone() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.index, RecommendType.matchZone, 0, 4);
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

    public List<Advertising> findAdvertising(int number) {
        List<Advertising> advertisingList = advertisingService.list(null, 0, number);
        return advertisingList;
    }

    public List<MatchZone> findMatchZone() {
        MatchZone matchZone = new MatchZone();
        return matchZoneService.list(matchZone, 0, 3);
    }

    public List<Info> findNewestInfo() {
        Info info = new Info();
        info.setVerify(1);
        return infoService.list(info, 0, 5);
    }

    public List<Info> findTradeInfo() {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.trade);
        info.setVerify(1);
        return infoService.list(info, 0, 5);
    }

    public List<Info> findMatchZoneInfo() {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setVerify(1);
        return infoService.list(info, 0, 5);
    }

    public List<MatchTeamWeb> findStarMatchTeam() {
        MatchTeam matchTeamRequest = new MatchTeam();
        List<MatchTeam> matchTeamList = matchTeamService.list(matchTeamRequest, 0, 100);
        List<MatchTeamWeb> matchTeamWebList = new ArrayList<>();
        for (MatchTeam matchTeam : matchTeamList) {
            MatchTeamWeb matchTeamWeb = new MatchTeamWeb();
            BeanUtils.copyProperties(matchTeam, matchTeamWeb);
            List<MatchZone> matchZoneList = new ArrayList<>();
            List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingService.listByMatchTeamId(matchTeam.getId());
            for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
                MatchZone matchZone = matchZoneService.findById(matchTeamMapping.getMatchZoneId());
                matchZoneList.add(matchZone);
            }
            matchTeamWeb.setMatchZoneList(matchZoneList);
            matchTeamWebList.add(matchTeamWeb);

        }
        return matchTeamWebList;
    }

    public List<AnchorZone> findAnchorZone() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.index, RecommendType.anchorZone, 0, 2);
        List<AnchorZone> anchorZoneList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            AnchorZone anchorZone = anchorZoneService.findById(recommendMapping.getEntityId());
            anchorZoneList.add(anchorZone);
        }
        return anchorZoneList;
    }

    public List<MatchZone> findMatchZoneVideo() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.matchZoneVideo, RecommendType.matchZone, 0, 4);
        List<MatchZone> matchZoneList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            MatchZone matchZone = matchZoneService.findById(recommendMapping.getEntityId());
            matchZoneList.add(matchZone);
        }
        return matchZoneList;
    }

    public List<Info> findMatchZoneVideoInfo() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.matchZoneVideo, RecommendType.matchZone, 0, 4);
        List<Long> zoneIdList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            zoneIdList.add(recommendMapping.getEntityId());
        }
        Info info = new Info();
        info.setZoneIdList(zoneIdList);
        return infoService.list(info, 0, 7);
    }
}
