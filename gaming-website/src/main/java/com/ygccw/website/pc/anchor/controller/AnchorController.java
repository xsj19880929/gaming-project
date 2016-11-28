package com.ygccw.website.pc.anchor.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.anchor.service.AnchorWebService;
import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.MatchZone;
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

    @RequestMapping(value = "/anchor.html", method = RequestMethod.GET)
    public String anchorList(HttpServletRequest request, final ModelMap model) {
        AnchorZone anchorZone = new AnchorZone();
        int currentPage = 1;
        int fetchSize = 1;
        String url = PageUtils.getPageUrl(request);
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
        int fetchSize = 1;
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
        int fetchSize = 1;
        String url = PageUtils.getPageUrl(request);
        model.put("pageFlag", "top");
        model.put("anchorZoneList", new FindResultToSale(anchorWebService.findAnchorZoneTop(anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), anchorWebService.findAnchorZoneTopSize(anchorZone), currentPage, fetchSize, url));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", platformId);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor/{id}.html", method = RequestMethod.GET)
    public String findById(final ModelMap model, @PathVariable Long id) {
        model.put("anchorZone", anchorWebService.findAnchorById(id));
        model.put("anchorMatchZoneList", anchorWebService.listMatchZoneListByAnchorZoneId(id));
        model.put("anchorZoneHonorList", anchorWebService.listAnchorZoneHonorByAnchorZoneId(id));
        model.put("anchorNewsList", anchorWebService.listInfoNewsAndTagByAnchorZoneId(id, 0, 5));
        model.put("anchorVideoList", anchorWebService.listInfoVideoByAnchorZoneId(id, 0, 8));
        model.put("anchorPictureList", anchorWebService.listPictureByAnchorZoneId(id, 0, 6));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(id, 0, 10));
        model.put("newsTopList", anchorWebService.listInfoNewsTop(0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        return "/view/anchor/anchor-index.html";
    }

    @RequestMapping(value = "/anchor/news-list/{anchorZoneId}.html", method = RequestMethod.GET)
    public String anchorNewsList(final ModelMap model, @PathVariable Long anchorZoneId) {
        model.put("anchorZone", anchorWebService.findAnchorById(anchorZoneId));
        model.put("anchorNewsList", anchorWebService.listInfoNewsAndTagByAnchorZoneId(anchorZoneId, 0, 5));
        model.put("anchorVideoTopList", anchorWebService.listInfoVideoTopByAnchorZoneId(anchorZoneId, 0, 10));
        model.put("anchorZoneTopList", anchorWebService.findAnchorZoneTop(new AnchorZone(), 0, 10));
        model.put("matchZoneTopList", anchorWebService.findMatchZoneTop(new MatchZone(), 0, 10));
        return "/view/anchor/anchor-news-list.html";
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
        return "/view/anchor/anchor-news-detail.html";
    }
}
