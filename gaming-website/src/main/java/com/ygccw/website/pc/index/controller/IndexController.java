package com.ygccw.website.pc.index.controller;

import com.ygccw.website.pc.index.service.IndexWebService;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.recommend.enums.RecommendLocal;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Controller
public class IndexController {
    @Inject
    private IndexWebService indexWebService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(final ModelMap model) {
        model.put("matchZoneList", indexWebService.findRecommendMatchZone());
        model.put("advImageList", indexWebService.findAdvertising(5));
        model.put("advWordList", indexWebService.findAdvertising(10));
        model.put("matchZoneNewList", indexWebService.findMatchZone());
        model.put("infoTradeList", indexWebService.findTradeInfo());
        model.put("infoNewestList", indexWebService.findNewestInfo());
        model.put("infoMatchZoneList", indexWebService.findMatchZoneInfo());
        model.put("starMatchTeamList", indexWebService.findStarMatchTeam());
        model.put("anchorZoneList", indexWebService.findAnchorZone());
        model.put("matchZoneVideoList", indexWebService.findMatchZoneVideo());
        model.put("infoMatchZoneVideoList", indexWebService.findZoneVideoInfoByRecommendLocal(RecommendLocal.matchZoneVideo, RecommendType.matchZone, InfoVideoType.matchVideo));
        model.put("infoAnchorZoneVideoList", indexWebService.findZoneVideoInfoByRecommendLocal(RecommendLocal.anchorZoneVideo, RecommendType.anchorZone, InfoVideoType.anchorVideo));
        model.put("anchorZoneVideoList", indexWebService.findAnchorZoneVideo());
        model.put("infoPlayerVideoInfoList", indexWebService.findPlayerVideoInfoList());
        model.put("matchZoneTopList", indexWebService.findTopMatchZoneList());
        model.put("pictureList", indexWebService.findNewestPictureList());
        model.put("recommendInfoList", indexWebService.findRecommendInfo());
        return "/index.html";
    }
}
