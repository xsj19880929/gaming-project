package com.ygccw.msite.mobile.game.controller;

import com.ygccw.msite.database.FindResultToSale;
import com.ygccw.msite.mobile.game.service.GameWebService;
import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.video.service.VideoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.enums.MatchStatus;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author soldier
 */
@Controller
public class GameController {
    @Inject
    private GameWebService gameWebService;
    @Inject
    private VideoWebService videoWebService;
    @Inject
    private MatchZoneService matchZoneService;
    @Inject
    private InfoService infoService;

    @RequestMapping(value = "/game/", method = RequestMethod.GET)
    public String gameList(final ModelMap model) {
        MatchZone matchZone = new MatchZone();
        int currentPage = 1;
        int fetchSize = 16;
        String url = "/game_new/0/0/all";
        model.put("pageFlag", "new");
        model.put("matchZoneList", new FindResultToSale(gameWebService.findMatchZoneNew(matchZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), gameWebService.findMatchZoneNewSize(matchZone), currentPage, fetchSize, url));
        model.put("matchZoneYearList", gameWebService.listMatchZoneYear());
        model.put("matchZoneAreaList", gameWebService.listMatchZoneArea());
        model.put("matchStatusList", gameWebService.listMatchStatus());
        model.put("matchZoneAreaIdSelected", 0);
        model.put("matchZoneYearIdSelected", 0);
        model.put("matchStatusSelected", "all");
        return "/view/game/game-list.html";
    }

    @RequestMapping(value = "/game_new/{matchZoneYearId}/{matchZoneAreaId}/{matchStatusStr}_{currentPage}.html", method = RequestMethod.GET)
    public String selectGameList(final ModelMap model, @PathVariable Long matchZoneYearId, @PathVariable Long matchZoneAreaId, @PathVariable String matchStatusStr, @PathVariable Integer currentPage) {
        MatchZone matchZone = common(model, matchZoneYearId, matchZoneAreaId, matchStatusStr);
        int fetchSize = 8;
        model.put("pageFlag", "new");
        String url = "/game_new/" + matchZoneYearId + "/" + matchZoneAreaId + "/" + matchStatusStr;
        model.put("matchZoneList", new FindResultToSale(gameWebService.findMatchZoneNew(matchZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), gameWebService.findMatchZoneNewSize(matchZone), currentPage, fetchSize, url));
        return "/view/game/game-list.html";
    }

    @RequestMapping(value = "/game_top/{matchZoneYearId}/{matchZoneAreaId}/{matchStatusStr}_{currentPage}.html", method = RequestMethod.GET)
    public String selectGameListTop(final ModelMap model, @PathVariable Long matchZoneYearId, @PathVariable Long matchZoneAreaId, @PathVariable String matchStatusStr, @PathVariable Integer currentPage) {
        MatchZone matchZone = common(model, matchZoneYearId, matchZoneAreaId, matchStatusStr);
        int fetchSize = 8;
        model.put("pageFlag", "top");
        String url = "/game_top/" + matchZoneYearId + "/" + matchZoneAreaId + "/" + matchStatusStr;
        model.put("matchZoneList", new FindResultToSale(gameWebService.findMatchZoneTop(matchZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), gameWebService.findMatchZoneTopSize(matchZone), currentPage, fetchSize, url));
        return "/view/game/game-list.html";
    }

    private MatchZone common(ModelMap model, Long matchZoneYearId, Long matchZoneAreaId, String matchStatusStr) {
        MatchZone matchZone = new MatchZone();
        if (matchZoneYearId != 0) {
            matchZone.setMatchZoneYearId(matchZoneYearId);
        }
        if (matchZoneAreaId != 0) {
            matchZone.setMatchZoneAreaId(matchZoneAreaId);
        }
        if (!"all".equals(matchStatusStr)) {
            for (MatchStatus matchStatus : MatchStatus.values()) {
                if (matchStatus.getName().equals(matchStatusStr)) {
                    matchZone.setMatchStatus(matchStatus);
                    model.put("matchStatusSelected", matchStatusStr);
                }
            }
        } else {
            model.put("matchStatusSelected", "all");
        }
        model.put("matchZoneAreaIdSelected", matchZoneAreaId);
        model.put("matchZoneYearIdSelected", matchZoneYearId);
        model.put("matchZoneYearList", gameWebService.listMatchZoneYear());
        model.put("matchZoneAreaList", gameWebService.listMatchZoneArea());
        model.put("matchStatusList", gameWebService.listMatchStatus());
        return matchZone;
    }

    @RequestMapping(value = "/game/{id}/", method = RequestMethod.GET)
    public String gameList(final ModelMap model, @PathVariable Long id) {
        model.put("matchZone", gameWebService.findById(id));
        model.put("matchTeamList", gameWebService.listMatchTeamByMatchZoneId(id));
        model.put("newsList", gameWebService.listInfoNewsByMatchZoneId(id, 0, 20));
        model.put("videoList", gameWebService.listInfoVideoByMatchZoneId(id, 0, 20));
        model.put("matchZoneBonusList", gameWebService.listMatchZoneBonusByMatchZoneId(id));
        model.put("matchZoneCalendarList", gameWebService.listMatchZoneCalendarByMatchZoneId(id));
        model.put("pictureList", gameWebService.listPictureByMatchZoneId(id, 0, 10));
        model.put("recommendVideoList", gameWebService.listInfoVideo(0, 2));
        model.put("recommendNewsList", gameWebService.listInfoNews(0, 10));
        matchZoneService.updateVisitCount(id);
        return "/view/game/game-index.html";
    }

    @RequestMapping(value = "/game/news/{matchZoneId}/", method = RequestMethod.GET)
    public String gameNewsList(final ModelMap model, @PathVariable Long matchZoneId) {
        int currentPage = 1;
        int fetchSize = 8;
        String url = "/game/news/" + matchZoneId + "/page";
        gameNewsListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-news-list.html";
    }

    @RequestMapping(value = "/game/news/{matchZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String gameNewsList(HttpServletRequest request, final ModelMap model, @PathVariable Long matchZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 8;
        String url = PageUtils.getPageUrl(request);
        gameNewsListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-news-list.html";
    }

    private void gameNewsListCommon(ModelMap model, Long matchZoneId, int currentPage, int fetchSize, String url) {
        MatchZone matchZone = new MatchZone();
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("newsList", new FindResultToSale(gameWebService.listInfoNewsAndTag(matchZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), gameWebService.listInfoNewsAndTagSize(matchZoneId), currentPage, fetchSize, url));
        model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
        model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(matchZone, 0, 2));
    }

    @RequestMapping(value = "/game/news/{newsId}.html", method = RequestMethod.GET)
    public String gameNews(final ModelMap model, @PathVariable Long newsId) {
        MatchZone matchZone = new MatchZone();
        InfoWeb infoWeb = gameWebService.findInfoById(newsId);
        model.put("info", infoWeb);
        model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
        model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
        model.put("pictureTopList", gameWebService.pictureListTop(0, 6));
        model.put("likeInfoList", gameWebService.likeInfoList(infoWeb, 10));
        model.put("nextInfo", gameWebService.nextInfo(infoWeb));
        model.put("lastInfo", gameWebService.lastInfo(infoWeb));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(matchZone, 0, 2));
        model.put("matchZone", gameWebService.findById(infoWeb.getZoneId()));
        infoService.updateVisitCount(newsId);
        return "/view/game/game-news-detail.html";
    }

    @RequestMapping(value = "/game/video/{matchZoneId}/", method = RequestMethod.GET)
    public String gameVideoList(final ModelMap model, @PathVariable Long matchZoneId) {
        int currentPage = 1;
        int fetchSize = 12;
        String url = "/game/video/" + matchZoneId + "/page";
        gameVideoListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-video-list.html";
    }

    @RequestMapping(value = "/game/video/{matchZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String gameVideoList(HttpServletRequest request, final ModelMap model, @PathVariable Long matchZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 12;
        String url = PageUtils.getPageUrl(request);
        gameVideoListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-video-list.html";
    }

    private void gameVideoListCommon(ModelMap model, Long matchZoneId, int currentPage, int fetchSize, String url) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setZoneId(matchZoneId);
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("matchVideoList", new FindResultToSale(videoWebService.videoList(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), videoWebService.videoListSize(info), currentPage, fetchSize, url));
        model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
        model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(new MatchZone(), 0, 2));
    }

    @RequestMapping(value = "/game/picture/{matchZoneId}/", method = RequestMethod.GET)
    public String gamePictureList(final ModelMap model, @PathVariable Long matchZoneId) {
        int currentPage = 1;
        int fetchSize = 9;
        String url = "/game/picture/" + matchZoneId + "/page";
        gamePictureListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-picture-list.html";
    }

    @RequestMapping(value = "/game/picture/{matchZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String gamePictureList(HttpServletRequest request, final ModelMap model, @PathVariable Long matchZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        String url = PageUtils.getPageUrl(request);
        gamePictureListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-picture-list.html";
    }

    private void gamePictureListCommon(ModelMap model, Long matchZoneId, int currentPage, int fetchSize, String url) {
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("matchPictureList", new FindResultToSale(gameWebService.gamePictureList(matchZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), gameWebService.gamePictureListSize(matchZoneId), currentPage, fetchSize, url));
        model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
        model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(new MatchZone(), 0, 2));
    }

    @RequestMapping(value = "/game/video/{id}.html", method = RequestMethod.GET)
    public String gameVideo(final ModelMap model, @PathVariable Long id) {
        MatchZone matchZone = new MatchZone();
        InfoWeb infoWeb = gameWebService.findInfoById(id);
        model.put("info", infoWeb);
        model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
        model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
        model.put("pictureTopList", gameWebService.pictureListTop(0, 6));
        model.put("likeInfoList", gameWebService.likeInfoList(infoWeb, 10));
        model.put("nextInfo", gameWebService.nextInfo(infoWeb));
        model.put("lastInfo", gameWebService.lastInfo(infoWeb));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(matchZone, 0, 2));
        model.put("matchZone", gameWebService.findById(infoWeb.getZoneId()));
        infoService.updateVisitCount(id);
        return "/view/game/game-video-detail.html";
    }

    @RequestMapping(value = "/game/team/{matchZoneId}/", method = RequestMethod.GET)
    public String gameTeamList(HttpServletRequest request, final ModelMap model, @PathVariable Long matchZoneId) {
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("matchTeamList", gameWebService.listMatchTeamByMatchZoneId(matchZoneId));
        return "/view/game/game-team-list.html";
    }
}
