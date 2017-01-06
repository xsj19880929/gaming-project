package com.ygccw.website.pc.index.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.index.service.IndexWebService;
import com.ygccw.website.pc.info.service.InfoWebService;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.recommend.enums.RecommendLocal;
import com.ygccw.wechat.common.recommend.enums.RecommendType;
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
public class IndexController {
    @Inject
    private IndexWebService indexWebService;
    @Inject
    private InfoWebService infoWebService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(final ModelMap model) {
        model.put("matchZoneList", indexWebService.findRecommendMatchZone());
        model.put("advImageList", indexWebService.findAdvertising(5));
        model.put("advWordList", indexWebService.findAdvertising(10));
        model.put("matchZoneNewList", indexWebService.findMatchZone());
        model.put("infoTradeList", indexWebService.findTradeInfo());
        model.put("infoNewestList", indexWebService.findNewestInfo());
        model.put("infoMatchZoneList", indexWebService.findMatchZoneInfo());
        model.put("infoAnchorZoneList", indexWebService.findAnchorZoneInfo());
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

    @RequestMapping(value = "/search_{keywords}_{type}_{currentPage}.html", method = RequestMethod.GET)
    public String search(HttpServletRequest request, final ModelMap model, @PathVariable String keywords, @PathVariable String type, @PathVariable Integer currentPage) {
        int fetchSize = 16;
        if ("all".equals(type)) {
            fetchSize = 4;
            model.put("infoList", new FindResultToSale(indexWebService.searchInfo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchInfoSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
            model.put("matchZoneList", new FindResultToSale(indexWebService.searchMatchZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchMatchZoneSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
            model.put("anchorZoneList", new FindResultToSale(indexWebService.searchAnchorZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchAnchorZoneSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
            model.put("videoList", new FindResultToSale(indexWebService.searchVideo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchVideoSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
            model.put("pictureList", new FindResultToSale(indexWebService.searchPicture(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchPictureSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        } else if ("news".equals(type)) {
            model.put("infoList", new FindResultToSale(indexWebService.searchInfo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchInfoSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        } else if ("video".equals(type)) {
            model.put("videoList", new FindResultToSale(indexWebService.searchVideo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchVideoSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        } else if ("match".equals(type)) {
            model.put("matchZoneList", new FindResultToSale(indexWebService.searchMatchZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchMatchZoneSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        } else if ("anchor".equals(type)) {
            model.put("anchorZoneList", new FindResultToSale(indexWebService.searchAnchorZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchAnchorZoneSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        } else if ("picture".equals(type)) {
            model.put("pictureList", new FindResultToSale(indexWebService.searchPicture(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), indexWebService.searchPictureSize(keywords), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        }
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        model.put("type", type);
        model.put("keywords", keywords);
        return "/search.html";
    }
}
