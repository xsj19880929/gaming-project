package com.ygccw.msite.mobile.game.controller;

import com.ygccw.msite.database.FindResultMoreToAjax;
import com.ygccw.msite.database.FindResultToMobile;
import com.ygccw.msite.mobile.common.model.HtmlTemplate;
import com.ygccw.msite.mobile.common.service.AjaxGetTemplateService;
import com.ygccw.msite.mobile.game.model.GameRequest;
import com.ygccw.msite.mobile.game.model.MatchTeamMappingRequest;
import com.ygccw.msite.mobile.game.service.GameWebService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
    @Inject
    private AjaxGetTemplateService ajaxGetTemplateService;

    @RequestMapping(value = "/game/", method = RequestMethod.GET)
    public String gameList(final ModelMap model) {
        MatchZone matchZone = new MatchZone();
        int fetchSize = 5;
        model.put("matchZoneList", new FindResultToMobile(gameWebService.findMatchZoneNew(matchZone, 0, fetchSize), fetchSize, ""));
        model.put("matchZoneYearList", gameWebService.listMatchZoneYear());
        model.put("matchZoneAreaList", gameWebService.listMatchZoneArea());
        model.put("matchStatusList", gameWebService.listMatchStatus());
        model.put("matchZoneAreaIdSelected", 0);
        model.put("matchZoneYearIdSelected", 0);
        model.put("matchStatusSelected", "all");
        return "/view/game/game-list.html";
    }

    @RequestMapping(value = "/game/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax listRest(@RequestBody GameRequest gameRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        MatchZone matchZone = common(new ModelMap(), gameRequest.getMatchZoneYearId(), gameRequest.getMatchZoneAreaId(), gameRequest.getMatchStatusStr());
        List<MatchZone> matchZoneList = gameWebService.findMatchZoneNew(matchZone, offset, fetchSize);
        HtmlTemplate htmlTemplate = ajaxGetTemplateService.getHtmlTemplate("game-list-template.html");
        return new FindResultMoreToAjax(matchZoneList, htmlTemplate);
    }

    @RequestMapping(value = "/game_new/{matchZoneYearId}/{matchZoneAreaId}/{matchStatusStr}_{currentPage}.html", method = RequestMethod.GET)
    public String selectGameList(final ModelMap model, @PathVariable Long matchZoneYearId, @PathVariable Long matchZoneAreaId, @PathVariable String matchStatusStr, @PathVariable Integer currentPage) {
        MatchZone matchZone = common(model, matchZoneYearId, matchZoneAreaId, matchStatusStr);
        int fetchSize = 5;
        model.put("matchZoneList", new FindResultToMobile(gameWebService.findMatchZoneNew(matchZone, 0, fetchSize), fetchSize, ""));
        return "/view/game/game-list.html";
    }

    @RequestMapping(value = "/game_top/{matchZoneYearId}/{matchZoneAreaId}/{matchStatusStr}_{currentPage}.html", method = RequestMethod.GET)
    public String selectGameListTop(final ModelMap model, @PathVariable Long matchZoneYearId, @PathVariable Long matchZoneAreaId, @PathVariable String matchStatusStr, @PathVariable Integer currentPage) {
        MatchZone matchZone = common(model, matchZoneYearId, matchZoneAreaId, matchStatusStr);
        int fetchSize = 5;
        model.put("matchZoneList", new FindResultToMobile(gameWebService.findMatchZoneNew(matchZone, 0, fetchSize), fetchSize, ""));
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
        model.put("newsList", gameWebService.listInfoNewsByMatchZoneId(id, 0, 4));
        model.put("videoList", gameWebService.listInfoVideoByMatchZoneId(id, 0, 4));
        model.put("matchZoneBonusList", gameWebService.listMatchZoneBonusByMatchZoneId(id));
        model.put("matchZoneCalendarList", gameWebService.listMatchZoneCalendarByMatchZoneId(id));
        model.put("nowTime", new Date());
        matchZoneService.updateVisitCount(id);
        return "/view/game/game-index.html";
    }

    @RequestMapping(value = "/game/news/{matchZoneId}/", method = RequestMethod.GET)
    public String gameNewsList(final ModelMap model, @PathVariable Long matchZoneId) {
        int currentPage = 1;
        int fetchSize = 4;
        gameNewsListCommon(model, matchZoneId, currentPage, fetchSize, "");
        return "/view/game/game-news-list.html";
    }

    @RequestMapping(value = "/game/news/{matchZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String gameNewsList(final ModelMap model, @PathVariable Long matchZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 4;
        gameNewsListCommon(model, matchZoneId, currentPage, fetchSize, "");
        return "/view/game/game-news-list.html";
    }

    private void gameNewsListCommon(ModelMap model, Long matchZoneId, int currentPage, int fetchSize, String url) {
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("newsList", new FindResultToMobile(gameWebService.listInfoNewsAndTag(matchZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, url));
    }


    @RequestMapping(value = "/game/video/{matchZoneId}/", method = RequestMethod.GET)
    public String gameVideoList(final ModelMap model, @PathVariable Long matchZoneId) {
        int currentPage = 1;
        int fetchSize = 10;
        String url = "/game/video/" + matchZoneId + "/page";
        gameVideoListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-video-list.html";
    }

    @RequestMapping(value = "/game/video/{matchZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String gameVideoList(HttpServletRequest request, final ModelMap model, @PathVariable Long matchZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        String url = PageUtils.getPageUrl(request);
        gameVideoListCommon(model, matchZoneId, currentPage, fetchSize, url);
        return "/view/game/game-video-list.html";
    }

    private void gameVideoListCommon(ModelMap model, Long matchZoneId, int currentPage, int fetchSize, String url) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.matchZone);
        info.setZoneId(matchZoneId);
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("matchVideoList", new FindResultToMobile(videoWebService.videoList(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, url));
    }

    @RequestMapping(value = "/game/picture/{matchZoneId}/", method = RequestMethod.GET)
    public String gamePictureList(final ModelMap model, @PathVariable Long matchZoneId) {
        int currentPage = 1;
        int fetchSize = 12;
        gamePictureListCommon(model, matchZoneId, currentPage, fetchSize, "");
        return "/view/game/game-picture-list.html";
    }

    @RequestMapping(value = "/game/picture/{matchZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String gamePictureList(final ModelMap model, @PathVariable Long matchZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 12;
        gamePictureListCommon(model, matchZoneId, currentPage, fetchSize, "");
        return "/view/game/game-picture-list.html";
    }

    private void gamePictureListCommon(ModelMap model, Long matchZoneId, int currentPage, int fetchSize, String url) {
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("matchPictureList", new FindResultToMobile(gameWebService.gamePictureList(matchZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, url));
    }


    @RequestMapping(value = "/game/team/{matchZoneId}/", method = RequestMethod.GET)
    public String gameTeamList(final ModelMap model, @PathVariable Long matchZoneId) {
        int fetchSize = 20;
        MatchTeamMappingRequest matchTeamMappingRequest = new MatchTeamMappingRequest();
        matchTeamMappingRequest.setMatchZoneId(matchZoneId);
        model.put("matchZone", gameWebService.findById(matchZoneId));
        model.put("matchTeamList", new FindResultToMobile(gameWebService.listMatchTeamByMatchTeamMapping(matchTeamMappingRequest, 0, fetchSize), fetchSize, ""));
        return "/view/game/game-team-list.html";
    }

    @RequestMapping(value = "/game/teamList", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax gameTeamListAjax(@RequestBody MatchTeamMappingRequest matchTeamMappingRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        return new FindResultMoreToAjax(gameWebService.listMatchTeamByMatchTeamMapping(matchTeamMappingRequest, offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(matchTeamMappingRequest.getTemplateName()));
    }
}
