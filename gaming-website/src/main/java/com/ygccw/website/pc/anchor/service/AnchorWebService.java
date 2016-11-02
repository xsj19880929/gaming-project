package com.ygccw.website.pc.anchor.service;

import com.ygccw.website.pc.anchor.model.AnchorZoneWeb;
import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.info.service.InfoWebService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.enums.PictureZoneType;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.AnchorZoneHonor;
import com.ygccw.wechat.common.zone.entity.AnchorZoneMatchZoneMapping;
import com.ygccw.wechat.common.zone.entity.AnchorZonePlatform;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneHonorService;
import com.ygccw.wechat.common.zone.service.AnchorZoneMatchZoneMappingService;
import com.ygccw.wechat.common.zone.service.AnchorZonePlatformService;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Inject
    private AnchorZoneHonorService anchorZoneHonorService;
    @Inject
    private InfoWebService infoWebService;
    @Inject
    private InfoService infoService;
    @Inject
    private PictureService pictureService;


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

    public AnchorZone findAnchorById(Long id) {
        return anchorZoneService.findById(id);
    }

    public List<MatchZone> listMatchZoneListByAnchorZoneId(Long anchorZoneId) {
        List<AnchorZoneMatchZoneMapping> anchorZoneMatchZoneMappingList = anchorZoneMatchZoneMappingService.listByAnchorZoneId(anchorZoneId);
        List<MatchZone> matchZoneList = new ArrayList<>();
        for (AnchorZoneMatchZoneMapping anchorZoneMatchZoneMapping : anchorZoneMatchZoneMappingList) {
            MatchZone matchZone = matchZoneService.findById(anchorZoneMatchZoneMapping.getMatchZoneId());
            matchZoneList.add(matchZone);
        }
        return matchZoneList;
    }

    public List<AnchorZoneHonor> listAnchorZoneHonorByAnchorZoneId(Long anchorZoneId) {
        return anchorZoneHonorService.listByAnchorZoneId(anchorZoneId);
    }

    public List<InfoWeb> listInfoNewsAndTagByAnchorZoneId(Long anchorZoneId, int offset, int fetchSize) {
        return infoWebService.infoList(anchorZoneId, InfoZoneType.anchorZone, TagZoneType.anchorZone, offset, fetchSize);
    }

    public List<Info> listInfoVideoByAnchorZoneId(Long anchorZoneId, int offset, int fetchSize) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
        info.setInfoType(InfoType.video);
        info.setZoneId(anchorZoneId);
        return infoService.list(info, offset, fetchSize);
    }

    public List<Picture> listPictureByAnchorZoneId(Long anchorZoneId, int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setSortName("visitCount");
        picture.setSortIfDesc(true);
        picture.setZoneId(anchorZoneId);
        picture.setPictureZoneType(PictureZoneType.anchorZone);
        return pictureService.list(picture, offset, fetchSize);
    }

    public List<Info> listInfoVideoTopByAnchorZoneId(Long anchorZoneId, int offset, int fetchSize) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
        info.setInfoType(InfoType.video);
        info.setZoneId(anchorZoneId);
        info.setSortIfDesc(true);
        info.setSortName("visitCount");
        return infoService.list(info, offset, fetchSize);
    }

    public List<Info> listInfoNewsTop(int offset, int fetchSize) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
        info.setInfoType(InfoType.news);
        info.setSortIfDesc(true);
        info.setSortName("visitCount");
        return infoService.list(info, offset, fetchSize);
    }

    public InfoWeb findInfoById(Long id) {
        return infoWebService.findById(id);
    }

    public Info lastInfo(InfoWeb infoWeb) {
        infoWeb.setZoneId(infoWeb.getZoneId());
        infoWeb.setInfoZoneType(infoWeb.getInfoZoneType());
        infoWeb.setInfoType(infoWeb.getInfoType());
        return infoService.lastInfo(infoWeb);
    }

    public Info nextInfo(InfoWeb infoWeb) {
        infoWeb.setZoneId(infoWeb.getZoneId());
        infoWeb.setInfoZoneType(infoWeb.getInfoZoneType());
        infoWeb.setInfoType(infoWeb.getInfoType());
        return infoService.nextInfo(infoWeb);
    }

    public Set<Info> likeInfoList(InfoWeb infoWeb, int fetchSize) {
        return infoWebService.likeInfoList(infoWeb, fetchSize);
    }

    public List<Picture> pictureListTop(int offset, int fetchSize) {
        return infoWebService.pictureListTop(offset, fetchSize);
    }

    public List<MatchZone> findMatchZoneTop(MatchZone matchZone, int offset, int fetchSize) {
        matchZone.setSortName("visitCount");
        matchZone.setSortIfDesc(true);
        return matchZoneService.list(matchZone, offset, fetchSize);
    }


}
