package com.ygccw.website.pc.game.controller;

import com.ygccw.website.pc.game.service.GameWebService;
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
        model.put("matchZoneListNew", gameWebService.findMatchZoneNew(matchZone, 0, 8));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(matchZone, 0, 8));
        model.put("matchZoneYearList", gameWebService.listMatchZoneYear());
        model.put("matchZoneAreaList", gameWebService.listMatchZoneArea());
        model.put("matchStatusList", gameWebService.listMatchStatus());
        model.put("matchZoneAreaIdSelected", 0);
        model.put("matchZoneYearIdSelected", 0);
        model.put("matchStatusSelected", "all");
        return "/view/game/game-list.html";
    }

    @RequestMapping(value = "/game/{matchZoneYearId}/{matchZoneAreaId}/{matchStatusStr}.html", method = RequestMethod.GET)
    public String selectGameList(final ModelMap model, @PathVariable Long matchZoneYearId, @PathVariable Long matchZoneAreaId, @PathVariable String matchStatusStr) {
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
        model.put("matchZoneListNew", gameWebService.findMatchZoneNew(matchZone, 0, 8));
        model.put("matchZoneListTop", gameWebService.findMatchZoneTop(matchZone, 0, 8));
        model.put("matchZoneYearList", gameWebService.listMatchZoneYear());
        model.put("matchZoneAreaList", gameWebService.listMatchZoneArea());
        model.put("matchStatusList", gameWebService.listMatchStatus());
        return "/view/game/game-list.html";
    }

    @RequestMapping(value = "/game/{id}.html", method = RequestMethod.GET)
    public String gameList(final ModelMap model, @PathVariable Long id) {
        model.put("matchZone", gameWebService.findById(id));
        model.put("matchTeamList", gameWebService.listMatchTeamByMatchZoneId(id));
        model.put("newsList", gameWebService.listInfoNewsByMatchZoneId(id));
        model.put("videoList", gameWebService.listInfoVideoByMatchZoneId(id));
        model.put("matchZoneBonusList", gameWebService.listMatchZoneBonusByMatchZoneId(id));
        model.put("matchZoneCalendarList", gameWebService.listMatchZoneCalendarByMatchZoneId(id));
        model.put("pictureList", gameWebService.listPictureByMatchZoneId(id));
        model.put("recommendVideoList", gameWebService.listInfoVideo());
        model.put("recommendNewsList", gameWebService.listInfoNews());
        return "/view/game/game-index.html";
    }
}
