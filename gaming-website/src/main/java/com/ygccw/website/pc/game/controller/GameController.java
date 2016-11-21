package com.ygccw.website.pc.game.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.game.service.GameWebService;
import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.enums.MatchStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Controller
public class GameController {
    @Inject
    private GameWebService gameWebService;

    @RequestMapping(value = "/game.html", method = RequestMethod.GET)
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

    @RequestMapping(value = "/game/{id}.html", method = RequestMethod.GET)
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
        return "/view/game/game-index.html";
    }

    @RequestMapping(value = "/game/news-list/{matchZoneId}.html", method = RequestMethod.GET)
    public String gameNewsList(final ModelMap model, @PathVariable Long matchZoneId) {
        MatchZone matchZone = new MatchZone();
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("newsList", gameWebService.listInfoNewsAndTag(matchZoneId, 0, 8));
        model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
        model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(matchZone, 0, 2));
        return "/view/game/game-news-list.html";
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
        return "/view/game/game-news-detail.html";
    }
}
