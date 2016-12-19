package com.ygccw.website.pc.info.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.info.service.InfoWebService;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagsService;
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
public class InfoController {
    @Inject
    private InfoWebService infoWebService;
    @Inject
    private TagsService tagsService;

    @RequestMapping(value = "/news.html", method = RequestMethod.GET)
    public String list(final ModelMap model) {
        int currentPage = 1;
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.trade, TagZoneType.trade, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), infoWebService.infoListSize(null, InfoZoneType.trade), currentPage, fetchSize, "/news_trade"));
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_trade_{currentPage}.html", method = RequestMethod.GET)
    public String tradeList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.trade, TagZoneType.trade, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), infoWebService.infoListSize(null, InfoZoneType.trade), currentPage, fetchSize, "/news_trade"));
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_match_{currentPage}.html", method = RequestMethod.GET)
    public String matchList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.matchZone, TagZoneType.matchZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), infoWebService.infoListSize(null, InfoZoneType.matchZone), currentPage, fetchSize, "/news_match"));
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_anchor_{currentPage}.html", method = RequestMethod.GET)
    public String anchorList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoList(null, InfoZoneType.anchorZone, TagZoneType.anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), infoWebService.infoListSize(null, InfoZoneType.anchorZone), currentPage, fetchSize, "/news_anchor"));
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        return "/view/news/news-list.html";
    }


    @RequestMapping(value = "/news/{id}.html", method = RequestMethod.GET)
    public String index(final ModelMap model, @PathVariable Long id) {
        InfoWeb infoWeb = infoWebService.findById(id);
        model.put("info", infoWeb);
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        model.put("pictureTopList", infoWebService.pictureListTop(0, 6));
        model.put("likeInfoList", infoWebService.likeInfoList(infoWeb, 10));
        model.put("nextInfo", infoWebService.nextInfo(infoWeb));
        model.put("lastInfo", infoWebService.lastInfo(infoWeb));
        return "/view/news/news-detail.html";
    }

    @RequestMapping(value = "/news/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        model.put("infoList", new FindResultToSale(infoWebService.infoListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), infoWebService.infoListByTagIdSize(tagId), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        model.put("tag", tagsService.findById(tagId));
        return "/view/news/news-tag-list.html";
    }
}
