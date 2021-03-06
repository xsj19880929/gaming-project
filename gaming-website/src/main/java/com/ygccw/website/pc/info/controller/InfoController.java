package com.ygccw.website.pc.info.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.anchor.service.AnchorWebService;
import com.ygccw.website.pc.common.service.RequestService;
import com.ygccw.website.pc.game.service.GameWebService;
import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.info.service.InfoWebService;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.advertising.enums.AdvType;
import com.ygccw.wechat.common.advertising.service.AdvertisingService;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagsService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author soldier
 */
@Controller
public class InfoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
    @Inject
    private RequestService requestService;
    @Inject
    private AdvertisingService advertisingService;

    @RequestMapping(value = "/news/", method = RequestMethod.GET)
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
    public String index(HttpServletRequest request, HttpServletResponse response, final ModelMap model, @PathVariable String id) {
        Long longId = 0L;
        try {
            longId = Long.parseLong(id);
        } catch (Exception e) {
            logger.error("新闻转型异常：{}", e.getMessage());
        }
        InfoWeb infoWeb = infoWebService.findById(longId);
        if (infoWeb == null || longId == 0) {
            //找不到新闻返回404
            requestService.redirectNoFound(request, response);
        }
        if (infoWeb != null) {
            infoService.updateVisitCount(longId); // 点击量
            model.put("info", infoWeb);

            if (infoWeb.getInfoZoneType() == InfoZoneType.trade) {
                model.put("newsAdvHead", advertisingService.findByAdvType(AdvType.newsDetailPcHead)); //文章头广告
                model.put("newsAdvRight", advertisingService.findByAdvType(AdvType.newsDetailPcRight)); //文章底广告
                model.put("newsTopList", infoWebService.newsListTop(0, 10));
                model.put("newsNewestList", infoWebService.newsListNewest(0, 10));
                model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
                model.put("videoTopList", infoWebService.videoListTop(0, 4));
                model.put("pictureTopList", infoWebService.pictureListTop(0, 6));
                model.put("likeInfoList", infoWebService.likeInfoList(infoWeb, 11));
                model.put("nextInfo", infoWebService.nextInfo(infoWeb));
                model.put("lastInfo", infoWebService.lastInfo(infoWeb));
                model.put("newsAdvImagePlus", advertisingService.findByAdvType(AdvType.imagePlus)); //图加广告
                return "/view/news/news-detail.html";
            } else if (infoWeb.getInfoZoneType() == InfoZoneType.matchZone) {
                model.put("newsAdvHead", advertisingService.findByAdvType(AdvType.newsDetailMatchPcHead)); //文章头广告
//            model.put("newsAdvBottom", advertisingService.findByAdvType(AdvType.newsDetailPcBottom)); //文章底广告
                model.put("newestNewsList", gameWebService.listInfoNews(0, 10));
                model.put("topNewsList", gameWebService.listInfoNewsTop(0, 10));
                model.put("pictureTopList", gameWebService.pictureListTop(0, 6));
                model.put("likeInfoList", gameWebService.likeInfoList(infoWeb, 11));
                model.put("nextInfo", gameWebService.nextInfo(infoWeb));
                model.put("lastInfo", gameWebService.lastInfo(infoWeb));
                model.put("matchZoneListTop", gameWebService.findMatchZoneTop(new MatchZone(), 0, 2));
                model.put("matchZone", gameWebService.findById(infoWeb.getZoneId()));
                model.put("newsAdvGameImagePlus", advertisingService.findByAdvType(AdvType.gameImagePlus)); //图加广告
                return "/view/game/game-news-detail.html";
            } else if (infoWeb.getInfoZoneType() == InfoZoneType.anchorZone) {
                model.put("newsAdvHead", advertisingService.findByAdvType(AdvType.newsDetailPcHead)); //文章头广告
                model.put("newsAdvRight", advertisingService.findByAdvType(AdvType.newsDetailAnchorPcRight)); //文章底广告
                model.put("anchorZone", anchorWebService.findAnchorById(infoWeb.getZoneId()));
                model.put("pictureTopList", anchorWebService.pictureListTop(0, 6));
                model.put("likeInfoList", anchorWebService.likeInfoList(infoWeb, 11));
                model.put("nextInfo", anchorWebService.nextInfo(infoWeb));
                model.put("lastInfo", anchorWebService.lastInfo(infoWeb));
                model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(infoWeb.getZoneId(), 0, 10));
                model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
                model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
                model.put("newsAdvImagePlus", advertisingService.findByAdvType(AdvType.imagePlus)); //图加广告
                return "/view/anchor/anchor-news-detail.html";
            }
        }
        return null;


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
