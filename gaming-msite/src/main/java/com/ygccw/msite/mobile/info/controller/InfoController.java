package com.ygccw.msite.mobile.info.controller;

import com.ygccw.msite.database.FindResultToSale;
import com.ygccw.msite.mobile.anchor.service.AnchorWebService;
import com.ygccw.msite.mobile.game.service.GameWebService;
import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.info.service.InfoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author soldier
 */
@Controller
public class InfoController {
    @Inject
    private InfoWebService infoWebService;
    @Inject
    private TagsService tagsService;
    @Inject
    private InfoService infoService;
    @Inject
    private GameWebService gameWebService;
    @Inject
    private AnchorWebService anchorWebService;

    @RequestMapping(value = "/news/", method = RequestMethod.GET)
    public String list(final ModelMap model) {
        int currentPage = 1;
        int fetchSize = 10;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.trade, TagZoneType.trade, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), 20, currentPage, fetchSize, "/news_trade"));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news/list", method = RequestMethod.POST)
    @ResponseBody
    public List<InfoWeb> listRest(@RequestParam String zoneType, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        return infoWebService.infoList(null, InfoZoneType.valueOf(zoneType), TagZoneType.valueOf(zoneType), offset, fetchSize);
    }

    @RequestMapping(value = "/news_trade_{currentPage}.html", method = RequestMethod.GET)
    public String tradeList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.trade, TagZoneType.trade, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), 20, currentPage, fetchSize, "/news_trade"));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_match_{currentPage}.html", method = RequestMethod.GET)
    public String matchList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.matchZone, TagZoneType.matchZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), 20, currentPage, fetchSize, "/news_match"));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_anchor_{currentPage}.html", method = RequestMethod.GET)
    public String anchorList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.anchorZone, TagZoneType.anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), 20, currentPage, fetchSize, "/news_anchor"));
        return "/view/news/news-list.html";
    }


    @RequestMapping(value = "/news/{id}.html", method = RequestMethod.GET)
    public String index(final ModelMap model, @PathVariable Long id) {
        InfoWeb infoWeb = infoWebService.findById(id);
        infoService.updateVisitCount(id); // 点击量
        model.put("info", infoWeb);
        if (infoWeb.getInfoZoneType() == InfoZoneType.trade) {
            model.put("newsTopList", infoWebService.newsListTop(0, 10));
            model.put("newsNewestList", infoWebService.newsListNewest(0, 10));
            model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
            model.put("videoTopList", infoWebService.videoListTop(0, 4));
            model.put("pictureTopList", infoWebService.pictureListTop(0, 6));
            model.put("likeInfoList", infoWebService.likeInfoList(infoWeb, 10));
            model.put("nextInfo", infoWebService.nextInfo(infoWeb));
            model.put("lastInfo", infoWebService.lastInfo(infoWeb));
            return "/view/news/news-detail.html";
        } else if (infoWeb.getInfoZoneType() == InfoZoneType.matchZone) {
            model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
            model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
            model.put("pictureTopList", gameWebService.pictureListTop(0, 6));
            model.put("likeInfoList", gameWebService.likeInfoList(infoWeb, 10));
            model.put("nextInfo", gameWebService.nextInfo(infoWeb));
            model.put("lastInfo", gameWebService.lastInfo(infoWeb));
            model.put("matchZoneListTop", gameWebService.findMatchZoneTop(new MatchZone(), 0, 2));
            model.put("matchZone", gameWebService.findById(infoWeb.getZoneId()));
            return "/view/game/game-news-detail.html";
        } else if (infoWeb.getInfoZoneType() == InfoZoneType.anchorZone) {
            model.put("anchorZone", anchorWebService.findAnchorById(infoWeb.getZoneId()));
            model.put("pictureTopList", anchorWebService.pictureListTop(0, 6));
            model.put("likeInfoList", anchorWebService.likeInfoList(infoWeb, 10));
            model.put("nextInfo", anchorWebService.nextInfo(infoWeb));
            model.put("lastInfo", anchorWebService.lastInfo(infoWeb));
            model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(infoWeb.getZoneId(), 0, 10));
            model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
            model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
            return "/view/anchor/anchor-news-detail.html";
        }

        return "/view/news/news-detail.html";


    }

    @RequestMapping(value = "/news/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), infoWebService.infoListByTagIdSize(tagId), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        Tags tags = tagsService.findById(tagId);
        model.put("tags", tags);
        return "/view/news/news-tag-list.html";
    }
}
