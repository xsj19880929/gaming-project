package com.ygccw.website.pc.anchor.service;

import com.ygccw.website.pc.anchor.model.AnchorZoneWeb;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;
import com.ygccw.wechat.common.zone.entity.AnchorZonePlatform;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneMatchZoneMappingService;
import com.ygccw.wechat.common.zone.service.AnchorZonePlatformService;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
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
public class AnchorWebService {
    @Inject
    private AnchorZoneService anchorZoneService;
    @Inject
    private AnchorZoneMatchZoneMappingService anchorZoneMatchZoneMappingService;
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private AnchorZonePlatformService anchorZonePlatformService;


    public List<AnchorZoneWeb> findAnchorZoneNew(AnchorZone anchorZone, int offset, int fetchSize) {
        return findAnchorZoneWeb(anchorZone, offset, fetchSize);
    }

    public List<AnchorZoneWeb> findAnchorZoneTop(AnchorZone anchorZone, int offset, int fetchSize) {
        anchorZone.setSortName("visitCount");
        anchorZone.setSortIfDesc(true);
        return findAnchorZoneWeb(anchorZone, offset, fetchSize);
    }

    private List<AnchorZoneWeb> findAnchorZoneWeb(AnchorZone anchorZone, int offset, int fetchSize) {
        List<AnchorZone> anchorZoneList = anchorZoneService.list(anchorZone, offset, fetchSize);
        List<AnchorZoneWeb> anchorZoneWebList = new ArrayList<>();
        for (AnchorZone anchorZone1 : anchorZoneList) {
            AnchorZoneWeb anchorZoneWeb = new AnchorZoneWeb();
            BeanUtils.copyProperties(anchorZone1, anchorZoneWeb);
            List<AnchorZoneMatchZoneMapping> anchorZoneMatchZoneMappingList = anchorZoneMatchZoneMappingService.listByAnchorZoneId(anchorZone1.getId());
            List<MatchZone> matchZoneList = new ArrayList<>();
            for (AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping : anchorZoneMatchZoneMappingList) {
                MatchZone matchZone = matchZoneService.findById(anchorZoneMatchZoneMapping.getMatchZoneId());
                matchZoneList.add(matchZone);
            }
            anchorZoneWeb.setMatchZoneList(matchZoneList);
            anchorZoneWebList.add(anchorZoneWeb);
        }

        return anchorZoneWebList;
    }

    public List<AnchorZonePlatform> listAnchorZonePlatform() {
        return anchorZonePlatformService.listAll();
    }


}
