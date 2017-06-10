package com.ygccw.msite.mobile.video.service;

import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.info.service.InfoWebService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Inject
    TagMappingService tagMappingService;
    @Inject
    TagsService tagsService;
    @Inject
    InfoWebService infoWebService;


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
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        info.setVerify(1);
        return infoService.list(info, offset, fetchSize);
    }

    public int videoListSize(Info info) {
        info.setInfoType(InfoType.video);
        info.setVerify(1);
        return infoService.listSize(info);
    }

    public List<Info> videoListTop(Info info, int offset, int fetchSize) {
        info.setSortIfDesc(true);
        info.setSortName("visitCount");
        info.setInfoType(InfoType.video);
        return infoService.list(info, offset, fetchSize);
    }

    public int videoListTopSize(Info info) {
        info.setSortIfDesc(true);
        info.setSortName("visitCount");
        info.setInfoType(InfoType.video);
        return infoService.listSize(info);
    }

    public InfoWeb findById(Long id) {
        return infoWebService.findById(id);
    }


    public List<AnchorZone> anchorListTop(int offset, int fetchSize) {
        AnchorZone anchorZone = new AnchorZone();
        anchorZone.setSortIfDesc(true);
        anchorZone.setSortName("visitCount");
        return anchorZoneService.list(anchorZone, offset, fetchSize);
    }

    public List<MatchZone> findTopMatchZoneList(int offset, int fetchSize) {
        return matchZoneService.listOrderByVisit(offset, fetchSize);

    }

    public Info lastInfo(InfoWeb infoWeb) {
        return infoWebService.lastInfo(infoWeb);
    }

    public Info nextInfo(InfoWeb infoWeb) {
        return infoWebService.nextInfo(infoWeb);
    }

    public List<Info> videoListByTagId(Long tagId, int offset, int fetchSize) {
        List<Info> infoList = new ArrayList<>();
        List<TagMapping> tagMappingList = tagMappingService.listByTagsIdPaging(tagId, offset, fetchSize);
        for (TagMapping tagMapping : tagMappingList) {
            Info info = infoService.findInfoById(tagMapping.getEntityId());
            if (info != null) {
                infoList.add(info);
            }
        }
        return infoList;
    }

    public int videoListByTagIdSize(Long tagId) {
        return tagMappingService.listByTagsIdPagingSize(tagId);
    }

    public Set<Info> likeInfoList(InfoWeb infoWeb, int fetchSize) {
        return infoWebService.likeInfoList(infoWeb, fetchSize);
    }
}
