package com.ygccw.msite.mobile.index.service;

import com.ygccw.msite.mobile.index.model.MatchTeamWeb;
import com.ygccw.msite.mobile.index.model.MatchZoneWeb;
import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.info.model.TagMappingWeb;
import com.ygccw.wechat.common.advertising.entity.Advertising;
import com.ygccw.wechat.common.advertising.service.AdvertisingService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.recommend.entity.RecommendMapping;
import com.ygccw.wechat.common.recommend.enums.RecommendLocal;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.recommend.service.RecommendMappingService;
import com.ygccw.wechat.common.tags.entity.TagMapping;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagMappingService;
import com.ygccw.wechat.common.tags.service.TagsService;
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
import java.util.Map;

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
    @Inject
    private PictureService pictureService;
    @Inject
    private TagMappingService tagMappingService;
    @Inject
    private TagsService tagsService;

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
        return advertisingService.list(null, 0, number);
    }

    public List<MatchZone> findMatchZone() {
        MatchZone matchZone = new MatchZone();
        return matchZoneService.list(matchZone, 0, 3);
    }

    public List<Info> findNewestInfo(int number) {
        Info info = new Info();
        info.setVerify(1);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        return infoService.list(info, 0, number);
    }

    public List<Info> findTradeInfo() {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.trade);
        info.setInfoType(InfoType.news);
        info.setVerify(1);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        return infoService.list(info, 0, 5);
    }

    public List<Info> findMatchZoneInfo() {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setInfoType(InfoType.news);
        info.setVerify(1);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        return infoService.list(info, 0, 5);
    }

    public List<Info> findAnchorZoneInfo() {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
        info.setInfoType(InfoType.news);
        info.setVerify(1);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
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

    public List<List<Info>> findMatchZoneVideoVideoInfoList(int size) {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.matchZoneVideo, RecommendType.matchZone, 0, 4);
        List<List<Info>> listData = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            Info info = new Info();
            info.setInfoVideoType(InfoVideoType.matchVideo);
            info.setVerify(1);
            info.setSortIfDesc(true);
            info.setSortName("publishTime");
            info.setInfoZoneType(InfoZoneType.matchZone);
            info.setZoneId(recommendMapping.getEntityId());
            listData.add(infoService.list(info, 0, size));
        }
        return listData;

    }

    public List<Info> findZoneVideoInfoByRecommendLocal(RecommendLocal recommendLocal, RecommendType recommendType, InfoVideoType infoVideoType) {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(recommendLocal, recommendType, 0, 7);
        List<Info> infoList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            infoList.add(infoService.findInfoById(recommendMapping.getEntityId()));
        }
        return infoList;
    }

    public List<Info> findVideoInfoList(InfoVideoType infoVideoType) {
        Info info = new Info();
        info.setInfoVideoType(infoVideoType);
        info.setVerify(1);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        return infoService.list(info, 0, 7);
    }

    public List<AnchorZone> findAnchorZoneVideo() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.anchorZoneVideo, RecommendType.anchorZone, 0, 4);
        List<AnchorZone> anchorZoneList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            AnchorZone anchorZone = anchorZoneService.findById(recommendMapping.getEntityId());
            anchorZoneList.add(anchorZone);
        }
        return anchorZoneList;
    }

    public List<List<Info>> findAnchorZoneVideoVideoInfoList(int size) {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.anchorZoneVideo, RecommendType.anchorZone, 0, 4);
        List<List<Info>> listData = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            Info info = new Info();
            info.setInfoVideoType(InfoVideoType.anchorVideo);
            info.setVerify(1);
            info.setSortIfDesc(true);
            info.setSortName("publishTime");
            info.setInfoZoneType(InfoZoneType.anchorZone);
            info.setZoneId(recommendMapping.getEntityId());
            listData.add(infoService.list(info, 0, size));
        }
        return listData;

    }


    public List<MatchZone> findTopMatchZoneList() {
        return matchZoneService.listOrderByVisit(0, 10);

    }

    public List<Picture> findNewestPictureList() {
        Picture picture = new Picture();
        picture.setVerify(1);
        return pictureService.list(picture, 0, 6);
    }

    //首页新闻推荐内容
    public List<Info> findRecommendInfo() {
        List<RecommendMapping> recommendMappingList = recommendMappingService.listByLocalAndType(RecommendLocal.index, RecommendType.news, 0, 20);
        List<Info> infoWebList = new ArrayList<>();
        for (RecommendMapping recommendMapping : recommendMappingList) {
            Info info = infoService.findInfoById(recommendMapping.getEntityId());
            infoWebList.add(info);
        }
        return infoWebList;
    }

    public List<InfoWeb> searchInfo(String keywords, int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setVerify(1);
        infoRequest.setTitle(keywords);
        infoRequest.setInfoType(InfoType.news);
        infoRequest.setSortIfDesc(true);
        infoRequest.setSortName("publishTime");
        List<Info> infoList = infoService.list(infoRequest, offset, fetchSize);
        List<InfoWeb> infoWebList = new ArrayList<>();
        for (Info info : infoList) {
            InfoWeb infoWeb = new InfoWeb();
            BeanUtils.copyProperties(info, infoWeb);
            TagMapping tagMappingRequest = new TagMapping();
            tagMappingRequest.setTagType(TagType.news);
            tagMappingRequest.setTagZoneType(TagZoneType.valueOf(info.getInfoZoneType().name()));
            tagMappingRequest.setEntityId(info.getId());
            List<TagMapping> tagMappingList = tagMappingService.list(tagMappingRequest, 0, 10);
            List<TagMappingWeb> tagMappingWebList = new ArrayList<>();
            for (TagMapping tagMapping : tagMappingList) {
                TagMappingWeb tagMappingWeb = new TagMappingWeb();
                BeanUtils.copyProperties(tagMapping, tagMappingWeb);
                Tags tags = tagsService.findById(tagMappingWeb.getTagsId());
                tagMappingWeb.setName(tags.getName());
                tagMappingWebList.add(tagMappingWeb);
            }
            if (!tagMappingWebList.isEmpty()) {
                infoWeb.setTagMappingWebList(tagMappingWebList);
            }
            infoWebList.add(infoWeb);
        }
        return infoWebList;
    }

    public int searchInfoSize(String keywords) {
        Info infoRequest = new Info();
        infoRequest.setVerify(1);
        infoRequest.setTitle(keywords);
        infoRequest.setInfoType(InfoType.news);
        return infoService.listSize(infoRequest);
    }

    public List<Info> searchVideo(String keywords, int offset, int fetchSize) {
        Info infoRequest = new Info();
        infoRequest.setVerify(1);
        infoRequest.setTitle(keywords);
        infoRequest.setInfoType(InfoType.video);
        infoRequest.setSortIfDesc(true);
        infoRequest.setSortName("publishTime");
        return infoService.list(infoRequest, offset, fetchSize);
    }

    public int searchVideoSize(String keywords) {
        Info infoRequest = new Info();
        infoRequest.setVerify(1);
        infoRequest.setTitle(keywords);
        infoRequest.setInfoType(InfoType.video);
        return infoService.listSize(infoRequest);
    }

    public List<MatchZone> searchMatchZone(String keywords, int offset, int fetchSize) {
        MatchZone matchZone = new MatchZone();
        matchZone.setName(keywords);
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public int searchMatchZoneSize(String keywords) {
        MatchZone matchZone = new MatchZone();
        matchZone.setName(keywords);
        return matchZoneService.listSize(matchZone);
    }

    public List<AnchorZone> searchAnchorZone(String keywords, int offset, int fetchSize) {
        AnchorZone anchorZone = new AnchorZone();
        anchorZone.setName(keywords);
        return anchorZoneService.list(anchorZone, offset, fetchSize);
    }

    public int searchAnchorZoneSize(String keywords) {
        AnchorZone anchorZone = new AnchorZone();
        anchorZone.setName(keywords);
        return anchorZoneService.listSize(anchorZone);
    }

    public List<Picture> searchPicture(String keywords, int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setDescription(keywords);
        picture.setVerify(1);
        return pictureService.list(picture, offset, fetchSize);
    }

    public int searchPictureSize(String keywords) {
        Picture picture = new Picture();
        picture.setDescription(keywords);
        picture.setVerify(1);
        return pictureService.listSize(picture);
    }

    public List<MatchZone> findSiteMapMatchZone(int offset, int fetchSize) {
        MatchZone matchZone = new MatchZone();
        matchZone.setSortIfDesc(true);
        matchZone.setSortName("visitCount");
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public List<Map<String, Object>> listSiteMapHotTags(int offset, int fetchSize) {
        TagMapping tagMapping = new TagMapping();
        tagMapping.setTagType(TagType.picture);
        return tagMappingService.listHotTags(tagMapping, offset, fetchSize);
    }

    public List<Info> findSiteMapNewestInfo(InfoType infoType, int offset, int fetchSize) {
        Info info = new Info();
        info.setVerify(1);
        info.setInfoType(infoType);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        return infoService.list(info, offset, fetchSize);
    }

    public List<MatchZone> findSiteMapNewestMatchZone(int offset, int fetchSize) {
        MatchZone matchZone = new MatchZone();
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public List<AnchorZone> findSiteMapNewestAnchorZone(int offset, int fetchSize) {
        return anchorZoneService.list(new AnchorZone(), offset, fetchSize);
    }

    public List<Picture> findSiteMapNewestPicture(int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setVerify(1);
        return pictureService.list(picture, offset, fetchSize);
    }

    public List<Info> findNewestInfoByInfoType(InfoType infoType, int number) {
        Info info = new Info();
        info.setVerify(1);
        info.setInfoType(infoType);
        info.setSortIfDesc(true);
        info.setSortName("publishTime");
        return infoService.list(info, 0, number);
    }


}
