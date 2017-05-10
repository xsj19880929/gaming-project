package com.ygccw.msite.mobile.anchor.controller;

import com.ygccw.msite.database.FindResultToSale;
import com.ygccw.msite.mobile.anchor.service.AnchorWebService;
import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.video.service.VideoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
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
public class AnchorController {
    @Inject
    private AnchorWebService anchorWebService;
    @Inject
    private VideoWebService videoWebService;
    @Inject
    private AnchorZoneService anchorZoneService;
    @Inject
    private InfoService infoService;

    @RequestMapping(value = "/anchor/", method = RequestMethod.GET)
    public String anchorList(final ModelMap model) {
        AnchorZone anchorZone = new AnchorZone();
        int currentPage = 1;
        int fetchSize = 20;
        String url = "/anchor_new/findPlatformId/0";
        model.put("pageFlag", "new");
        model.put("anchorZoneList", new FindResultToSale(anchorWebService.findAnchorZoneNew(anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), anchorWebService.findAnchorZoneNewSize(anchorZone), currentPage, fetchSize, url));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", 0);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor_new/findPlatformId/{platformId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectAnchorList(HttpServletRequest request, final ModelMap model, @PathVariable Long platformId, @PathVariable Integer currentPage) {
        AnchorZone anchorZone = new AnchorZone();
        if (platformId != 0) {
            anchorZone.setPlatformId(platformId);
        }
        int fetchSize = 20;
        String url = PageUtils.getPageUrl(request);
        model.put("pageFlag", "new");
        model.put("anchorZoneList", new FindResultToSale(anchorWebService.findAnchorZoneNew(anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), anchorWebService.findAnchorZoneNewSize(anchorZone), currentPage, fetchSize, url));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", platformId);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor_top/findPlatformId/{platformId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectAnchorListTop(HttpServletRequest request, final ModelMap model, @PathVariable Long platformId, @PathVariable Integer currentPage) {
        AnchorZone anchorZone = new AnchorZone();
        if (platformId != 0) {
            anchorZone.setPlatformId(platformId);
        }
        int fetchSize = 20;
        String url = PageUtils.getPageUrl(request);
        model.put("pageFlag", "top");
        model.put("anchorZoneList", new FindResultToSale(anchorWebService.findAnchorZoneTop(anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), anchorWebService.findAnchorZoneTopSize(anchorZone), currentPage, fetchSize, url));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", platformId);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor/{id}/", method = RequestMethod.GET)
    public String findById(final ModelMap model, @PathVariable Long id) {
        model.put("anchorZone", anchorWebService.findAnchorById(id));
        model.put("anchorMatchZoneList", anchorWebService.listMatchZoneListByAnchorZoneId(id));
        model.put("anchorZoneHonorList", anchorWebService.listAnchorZoneHonorByAnchorZoneId(id));
        model.put("anchorNewsList", anchorWebService.listInfoNewsAndTagByAnchorZoneId(id, 0, 5));
        model.put("anchorVideoList", anchorWebService.listInfoVideoByAnchorZoneId(id, 0, 12));
        model.put("anchorPictureList", anchorWebService.listPictureByAnchorZoneId(id, 0, 6));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(id, 0, 10));
        model.put("newsTopList", anchorWebService.listInfoNewsTop(0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        anchorZoneService.updateVisitCount(id);
        return "/view/anchor/anchor-index.html";
    }

    @RequestMapping(value = "/anchor/news/{anchorZoneId}/", method = RequestMethod.GET)
    public String anchorNewsListIndex(final ModelMap model, @PathVariable Long anchorZoneId) {
        int currentPage = 1;
        int fetchSize = 5;
        String url = "/anchor/news/" + anchorZoneId + "/page";
        anchorNewsListCommon(model, anchorZoneId, currentPage, fetchSize, url);
        return "/view/anchor/anchor-news-list.html";
    }

    @RequestMapping(value = "/anchor/news/{anchorZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String anchorNewsList(HttpServletRequest request, final ModelMap model, @PathVariable Long anchorZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 5;
        String url = PageUtils.getPageUrl(request);
        anchorNewsListCommon(model, anchorZoneId, currentPage, fetchSize, url);
        return "/view/anchor/anchor-news-list.html";
    }

    private void anchorNewsListCommon(ModelMap model, Long anchorZoneId, int currentPage, int fetchSize, String url) {

        model.put("anchorZone", anchorWebService.findAnchorById(anchorZoneId));
        model.put("anchorNewsList", new FindResultToSale(anchorWebService.listInfoNewsAndTagByAnchorZoneId(anchorZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), anchorWebService.listInfoNewsAndTagByAnchorZoneIdSize(anchorZoneId), currentPage, fetchSize, url));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(anchorZoneId, 0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
    }

    @RequestMapping(value = "/anchor/news/{id}.html", method = RequestMethod.GET)
    public String findAnchorNews(final ModelMap model, @PathVariable Long id) {
        InfoWeb info = anchorWebService.findInfoById(id);
        model.put("info", info);
        model.put("anchorZone", anchorWebService.findAnchorById(info.getZoneId()));
        model.put("pictureTopList", anchorWebService.pictureListTop(0, 6));
        model.put("likeInfoList", anchorWebService.likeInfoList(info, 10));
        model.put("nextInfo", anchorWebService.nextInfo(info));
        model.put("lastInfo", anchorWebService.lastInfo(info));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(info.getZoneId(), 0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
        infoService.updateVisitCount(id);
        return "/view/anchor/anchor-news-detail.html";
    }

    @RequestMapping(value = "/anchor/video/{anchorZoneId}/", method = RequestMethod.GET)
    public String anchorVideoList(final ModelMap model, @PathVariable Long anchorZoneId) {
        int currentPage = 1;
        int fetchSize = 9;
        String url = "/anchor/video/" + anchorZoneId + "/page";
        anchorVideoCommon(model, anchorZoneId, currentPage, fetchSize, url);
        return "/view/anchor/anchor-video-list.html";
    }

    @RequestMapping(value = "/anchor/video/{anchorZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String anchorVideoList(HttpServletRequest request, final ModelMap model, @PathVariable Long anchorZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        String url = PageUtils.getPageUrl(request);
        anchorVideoCommon(model, anchorZoneId, currentPage, fetchSize, url);
        return "/view/anchor/anchor-video-list.html";
    }

    private void anchorVideoCommon(ModelMap model, Long anchorZoneId, int currentPage, int fetchSize, String url) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
        info.setZoneId(anchorZoneId);
        model.put("anchorZone", anchorWebService.findAnchorById(anchorZoneId));
        model.put("anchorVideoList", new FindResultToSale(videoWebService.videoList(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), videoWebService.videoListSize(info), currentPage, fetchSize, url));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(anchorZoneId, 0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
    }

    @RequestMapping(value = "/anchor/video/{id}.html", method = RequestMethod.GET)
    public String findAnchorVideo(final ModelMap model, @PathVariable Long id) {
        InfoWeb info = anchorWebService.findInfoById(id);
        model.put("info", info);
        model.put("anchorZone", anchorWebService.findAnchorById(info.getZoneId()));
        model.put("pictureTopList", anchorWebService.pictureListTop(0, 6));
        model.put("likeInfoList", anchorWebService.likeInfoList(info, 10));
        model.put("nextInfo", anchorWebService.nextInfo(info));
        model.put("lastInfo", anchorWebService.lastInfo(info));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(info.getZoneId(), 0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
        infoService.updateVisitCount(id);
        return "/view/anchor/anchor-video-detail.html";
    }

    @RequestMapping(value = "/anchor/picture/{anchorZoneId}/", method = RequestMethod.GET)
    public String anchorPictureList(final ModelMap model, @PathVariable Long anchorZoneId) {
        int currentPage = 1;
        int fetchSize = 9;
        String url = "/anchor/picture/" + anchorZoneId + "/page";
        anchorPictureCommon(model, anchorZoneId, currentPage, fetchSize, url);
        return "/view/anchor/anchor-picture-list.html";
    }

    @RequestMapping(value = "/anchor/picture/{anchorZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String anchorPictureList(HttpServletRequest request, final ModelMap model, @PathVariable Long anchorZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 9;
        String url = PageUtils.getPageUrl(request);
        anchorPictureCommon(model, anchorZoneId, currentPage, fetchSize, url);
        return "/view/anchor/anchor-picture-list.html";
    }

    private void anchorPictureCommon(ModelMap model, Long anchorZoneId, int currentPage, int fetchSize, String url) {
        model.put("anchorZone", anchorWebService.findAnchorById(anchorZoneId));
        model.put("anchorPictureList", new FindResultToSale(anchorWebService.anchorPictureList(anchorZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), anchorWebService.anchorPictureListSize(anchorZoneId), currentPage, fetchSize, url));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(anchorZoneId, 0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
    }

}
