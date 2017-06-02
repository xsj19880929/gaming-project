package com.ygccw.msite.mobile.anchor.controller;

import com.ygccw.msite.database.FindResultMoreToAjax;
import com.ygccw.msite.database.FindResultToMobile;
import com.ygccw.msite.mobile.anchor.model.AnchorRequest;
import com.ygccw.msite.mobile.anchor.model.AnchorZoneWeb;
import com.ygccw.msite.mobile.anchor.service.AnchorWebService;
import com.ygccw.msite.mobile.common.model.HtmlTemplate;
import com.ygccw.msite.mobile.common.service.AjaxGetTemplateService;
import com.ygccw.msite.mobile.video.service.VideoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

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
    @Inject
    private AjaxGetTemplateService ajaxGetTemplateService;

    @RequestMapping(value = "/anchor/", method = RequestMethod.GET)
    public String anchorList(final ModelMap model) {
        AnchorZone anchorZone = new AnchorZone();
        int fetchSize = 12;
        model.put("pageFlag", "new");
        model.put("anchorZoneList", new FindResultToMobile(anchorWebService.findAnchorZoneNew(anchorZone, 0, fetchSize), fetchSize, ""));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", 0);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax listRest(@RequestBody AnchorRequest anchorRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        AnchorZone anchorZone = new AnchorZone();
        if (anchorRequest.getPlatformId() != 0) {
            anchorZone.setPlatformId(anchorRequest.getPlatformId());
        }
        anchorZone.setSortIfDesc(anchorRequest.getSortIfDesc());
        anchorZone.setSortName(anchorRequest.getSortName());
        List<AnchorZoneWeb> anchorZoneWebList = anchorWebService.findAnchorZoneNew(anchorZone, offset, fetchSize);
        HtmlTemplate htmlTemplate = ajaxGetTemplateService.getHtmlTemplate("anchor-list-template.html");
        return new FindResultMoreToAjax(anchorZoneWebList, htmlTemplate);
    }


    @RequestMapping(value = "/anchor_new/findPlatformId/{platformId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectAnchorList(final ModelMap model, @PathVariable Long platformId, @PathVariable Integer currentPage) {
        AnchorZone anchorZone = new AnchorZone();
        if (platformId != 0) {
            anchorZone.setPlatformId(platformId);
        }
        int fetchSize = 12;
        model.put("pageFlag", "new");
        model.put("anchorZoneList", new FindResultToMobile(anchorWebService.findAnchorZoneNew(anchorZone, 0, fetchSize), fetchSize, ""));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", platformId);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor_top/findPlatformId/{platformId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectAnchorListTop(final ModelMap model, @PathVariable Long platformId, @PathVariable Integer currentPage) {
        AnchorZone anchorZone = new AnchorZone();
        if (platformId != 0) {
            anchorZone.setPlatformId(platformId);
        }
        int fetchSize = 12;
        model.put("pageFlag", "top");
        model.put("anchorZoneList", new FindResultToMobile(anchorWebService.findAnchorZoneNew(anchorZone, 0, fetchSize), fetchSize, ""));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", platformId);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor/{id}/", method = RequestMethod.GET)
    public String findById(final ModelMap model, @PathVariable Long id) {
        model.put("anchorZone", anchorWebService.findAnchorZoneWebById(id));
        model.put("anchorNewsList", anchorWebService.listInfoNewsAndTagByAnchorZoneId(id, 0, 4));
        model.put("anchorVideoList", anchorWebService.listInfoVideoByAnchorZoneId(id, 0, 4));
        model.put("anchorPictureList", anchorWebService.listPictureByAnchorZoneId(id, 0, 4));
        anchorZoneService.updateVisitCount(id);
        return "/view/anchor/anchor-index.html";
    }

    @RequestMapping(value = "/anchor/news/{anchorZoneId}/", method = RequestMethod.GET)
    public String anchorNewsListIndex(final ModelMap model, @PathVariable Long anchorZoneId) {
        int currentPage = 1;
        int fetchSize = 4;
        anchorNewsListCommon(model, anchorZoneId, currentPage, fetchSize, "");
        return "/view/anchor/anchor-news-list.html";
    }

    @RequestMapping(value = "/anchor/news/{anchorZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String anchorNewsList(final ModelMap model, @PathVariable Long anchorZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 4;
        anchorNewsListCommon(model, anchorZoneId, currentPage, fetchSize, "");
        return "/view/anchor/anchor-news-list.html";
    }

    private void anchorNewsListCommon(ModelMap model, Long anchorZoneId, int currentPage, int fetchSize, String url) {
        model.put("anchorZone", anchorWebService.findAnchorZoneWebById(anchorZoneId));
        model.put("anchorNewsList", new FindResultToMobile(anchorWebService.listInfoNewsAndTagByAnchorZoneId(anchorZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, url));
    }

    @RequestMapping(value = "/anchor/video/{anchorZoneId}/", method = RequestMethod.GET)
    public String anchorVideoList(final ModelMap model, @PathVariable Long anchorZoneId) {
        int currentPage = 1;
        int fetchSize = 4;
        anchorVideoCommon(model, anchorZoneId, currentPage, fetchSize, "");
        return "/view/anchor/anchor-video-list.html";
    }

    @RequestMapping(value = "/anchor/video/{anchorZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String anchorVideoList(final ModelMap model, @PathVariable Long anchorZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 4;
        anchorVideoCommon(model, anchorZoneId, currentPage, fetchSize, "");
        return "/view/anchor/anchor-video-list.html";
    }

    private void anchorVideoCommon(ModelMap model, Long anchorZoneId, int currentPage, int fetchSize, String url) {
        Info info = new Info();
        info.setInfoZoneType(InfoZoneType.anchorZone);
        info.setZoneId(anchorZoneId);
        model.put("anchorZone", anchorWebService.findAnchorZoneWebById(anchorZoneId));
        model.put("anchorVideoList", new FindResultToMobile(videoWebService.videoList(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, url));
    }


    @RequestMapping(value = "/anchor/picture/{anchorZoneId}/", method = RequestMethod.GET)
    public String anchorPictureList(final ModelMap model, @PathVariable Long anchorZoneId) {
        int currentPage = 1;
        int fetchSize = 12;
        anchorPictureCommon(model, anchorZoneId, currentPage, fetchSize, "");
        return "/view/anchor/anchor-picture-list.html";
    }

    @RequestMapping(value = "/anchor/picture/{anchorZoneId}/page_{currentPage}.html", method = RequestMethod.GET)
    public String anchorPictureList(final ModelMap model, @PathVariable Long anchorZoneId, @PathVariable Integer currentPage) {
        int fetchSize = 12;
        anchorPictureCommon(model, anchorZoneId, currentPage, fetchSize, "");
        return "/view/anchor/anchor-picture-list.html";
    }

    private void anchorPictureCommon(ModelMap model, Long anchorZoneId, int currentPage, int fetchSize, String url) {
        model.put("anchorZone", anchorWebService.findAnchorZoneWebById(anchorZoneId));
        model.put("anchorPictureList", new FindResultToMobile(anchorWebService.anchorPictureList(anchorZoneId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, url));
    }

}
