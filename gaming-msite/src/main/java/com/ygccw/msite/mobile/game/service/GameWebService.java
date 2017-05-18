package com.ygccw.msite.mobile.game.service;

import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.info.service.InfoWebService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.enums.PictureZoneType;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchTeamMapping;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneArea;
import com.ygccw.wechat.common.zone.entity.MatchZoneBonus;
import com.ygccw.wechat.common.zone.entity.MatchZoneCalendar;
import com.ygccw.wechat.common.zone.entity.MatchZoneYear;
import com.ygccw.wechat.common.zone.enums.MatchStatus;
import com.ygccw.wechat.common.zone.service.MatchTeamMappingService;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import com.ygccw.wechat.common.zone.service.MatchZoneAreaService;
import com.ygccw.wechat.common.zone.service.MatchZoneBonusService;
import com.ygccw.wechat.common.zone.service.MatchZoneCalendarService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneYearService;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Inject
    private MatchZoneBonusService matchZoneBonusService;
    @Inject
    private MatchZoneCalendarService matchZoneCalendarService;
    @Inject
    private InfoService infoService;
    @Inject
    private PictureService pictureService;
    @Inject
    private InfoWebService infoWebService;


    public List<MatchZone> findMatchZoneNew(MatchZone matchZone, int offset, int fetchSize) {
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public int findMatchZoneNewSize(MatchZone matchZone) {
        return matchZoneService.listSize(matchZone);
    }

    public List<MatchZone> findMatchZoneTop(MatchZone matchZone, int offset, int fetchSize) {
        matchZone.setSortName("visitCount");
        matchZone.setSortIfDesc(true);
        return matchZoneService.list(matchZone, offset, fetchSize);
    }

    public int findMatchZoneTopSize(MatchZone matchZone) {
        matchZone.setSortName("visitCount");
        matchZone.setSortIfDesc(true);
        return matchZoneService.listSize(matchZone);
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

    public List<MatchTeam> listMatchTeamByMatchZoneId(Long matchZoneId) {
        List<MatchTeamMapping> matchTeamMappingList = matchTeamMappingService.listByMatchZoneId(matchZoneId);
        List<MatchTeam> matchTeamList = new ArrayList<>();
        for (MatchTeamMapping matchTeamMapping : matchTeamMappingList) {
            MatchTeam matchTeam = matchTeamService.findById(matchTeamMapping.getMatchTeamId());
            matchTeamList.add(matchTeam);
        }
        return matchTeamList;
    }

    public List<MatchZoneBonus> listMatchZoneBonusByMatchZoneId(Long matchZoneId) {
        return matchZoneBonusService.listByMatchZoneId(matchZoneId);
    }

    public List<MatchZoneCalendar> listMatchZoneCalendarByMatchZoneId(Long matchZoneId) {
        return matchZoneCalendarService.listByMatchZoneId(matchZoneId);
    }

    public List<Info> listInfoVideoByMatchZoneId(Long matchZoneId, int offset, int fetchSize) {
        Info info = new Info();
        info.setZoneId(matchZoneId);
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setInfoType(InfoType.video);
        return infoService.list(info, offset, fetchSize);
    }

    public List<Info> listInfoNewsByMatchZoneId(Long matchZoneId, int offset, int fetchSize) {
        Info info = new Info();
        info.setZoneId(matchZoneId);
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setInfoType(InfoType.news);
        return infoService.list(info, offset, fetchSize);
    }

    public List<Picture> listPictureByMatchZoneId(Long matchZoneId, int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setPictureZoneType(PictureZoneType.matchZone);
        picture.setZoneId(matchZoneId);
        return pictureService.list(picture, offset, fetchSize);
    }

    public List<Info> listInfoVideo(int offset, int fetchSize) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setInfoType(InfoType.video);
        return infoService.list(info, offset, fetchSize);
    }

    public List<Info> listInfoNews(int offset, int fetchSize) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setInfoType(InfoType.news);
        return infoService.list(info, offset, fetchSize);
    }

    public List<Info> listInfoNewsAndTag(Long matchZoneId, int offset, int fetchSize) {
        return infoWebService.infoList(matchZoneId, InfoZoneType.matchZone, TagZoneType.matchZone, offset, fetchSize);
    }

    public int listInfoNewsAndTagSize(Long matchZoneId) {
        return infoWebService.infoListSize(matchZoneId, InfoZoneType.matchZone);
    }

    public List<Info> listInfoNewsTop(int offset, int fetchSize) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
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

    public List<Picture> gamePictureList(Long matchZoneId, int offset, int fetchSize) {
        Picture picture = new Picture();
        picture.setPictureZoneType(PictureZoneType.matchZone);
        picture.setZoneId(matchZoneId);
        return pictureService.list(picture, offset, fetchSize);
    }

    public int gamePictureListSize(Long matchZoneId) {
        Picture picture = new Picture();
        picture.setPictureZoneType(PictureZoneType.matchZone);
        picture.setZoneId(matchZoneId);
        return pictureService.listSize(picture);
    }


}
