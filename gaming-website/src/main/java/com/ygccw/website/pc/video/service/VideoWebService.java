package com.ygccw.website.pc.video.service;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class VideoWebService {
    @Inject
    InfoService infoService;
    @Inject
    AnchorZoneService anchorZoneService;
    @Inject
    MatchZoneService matchZoneService;


    public List<MatchZone> matchZoneList(int offset, int fetchSize) {
        MatchZone matchZone = new MatchZone();
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public List<AnchorZone> anchorList(int offset, int fetchSize) {
        AnchorZone anchorZone = new AnchorZone();
        return anchorZoneService.list(anchorZone, offset, fetchSize);
    }

    public List<Info> videoList(Info info, int offset, int fetchSize) {
        info.setInfoType(InfoType.video);
        return infoService.list(info, offset, fetchSize);
    }

    public List<Info> videoListTop(Info info, int offset, int fetchSize) {
        info.setSortIfDesc(true);
        info.setSortName("visitCount");
        info.setInfoType(InfoType.video);
        return infoService.list(info, offset, fetchSize);
    }
}
