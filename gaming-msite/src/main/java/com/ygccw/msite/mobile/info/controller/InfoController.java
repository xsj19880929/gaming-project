package com.ygccw.msite.mobile.info.controller;

import com.ygccw.msite.database.FindResultMoreToAjax;
import com.ygccw.msite.database.FindResultToMobile;
import com.ygccw.msite.mobile.anchor.service.AnchorWebService;
import com.ygccw.msite.mobile.common.service.AjaxGetTemplateService;
import com.ygccw.msite.mobile.common.service.RequestService;
import com.ygccw.msite.mobile.game.service.GameWebService;
import com.ygccw.msite.mobile.info.model.InfoRequest;
import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.info.service.InfoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.common.tags.entity.Tags;
import com.ygccw.wechat.common.tags.enums.TagZoneType;
import com.ygccw.wechat.common.tags.service.TagsService;
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
import javax.servlet.http.HttpServletResponse;

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
    @Inject
    private AjaxGetTemplateService ajaxGetTemplateService;
    @Inject
    private RequestService requestService;

    @RequestMapping(value = "/news/", method = RequestMethod.GET)
    public String list(final ModelMap model) {
        int fetchSize = 10;
        model.put("infoList", new FindResultToMobile(infoWebService.infoList(null, InfoZoneType.trade, TagZoneType.trade, 0, fetchSize), fetchSize, "/news_trade"));
        return "/view/news/news-list.html";
    }

    /**
     * 所有新闻ajax请求
     *
     * @param infoRequest
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/news/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax listRest(@RequestBody InfoRequest infoRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        infoRequest.setVerify(1);
        infoRequest.setInfoType(InfoType.news);
        return new FindResultMoreToAjax(infoWebService.infoListOutCondition(infoRequest, offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(infoRequest.getTemplateName()));
    }

    @RequestMapping(value = "/news_trade_{currentPage}.html", method = RequestMethod.GET)
    public String tradeList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        model.put("infoList", new FindResultToMobile(infoWebService.infoList(null, InfoZoneType.trade, TagZoneType.trade, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, "/news_trade"));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_match_{currentPage}.html", method = RequestMethod.GET)
    public String matchList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        model.put("infoList", new FindResultToMobile(infoWebService.infoList(null, InfoZoneType.matchZone, TagZoneType.matchZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, "/news_match"));
        return "/view/news/news-list.html";
    }

    @RequestMapping(value = "/news_anchor_{currentPage}.html", method = RequestMethod.GET)
    public String anchorList(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        model.put("infoList", new FindResultToMobile(infoWebService.infoList(null, InfoZoneType.anchorZone, TagZoneType.anchorZone, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, "/news_anchor"));
        return "/view/news/news-list.html";
    }


    @RequestMapping(value = "/news/{id}.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, final ModelMap model, @PathVariable Long id) {
        InfoWeb infoWeb = infoWebService.findById(id);
        if (infoWeb == null) {
            //找不到新闻返回404
            requestService.redirectNoFound(request, response);
        }
        infoService.updateVisitCount(id); // 点击量
        model.put("info", infoWeb);
        if (infoWeb.getInfoZoneType() == InfoZoneType.trade) {
            model.put("likeInfoList", infoWebService.likeInfoList(infoWeb, 10));
            return "/view/news/news-detail.html";
        } else if (infoWeb.getInfoZoneType() == InfoZoneType.matchZone) {
            model.put("pictureTopList", gameWebService.pictureListTop(0, 6));
            model.put("likeInfoList", gameWebService.likeInfoList(infoWeb, 10));
            model.put("matchZone", gameWebService.findById(infoWeb.getZoneId()));
            return "/view/game/game-news-detail.html";
        } else if (infoWeb.getInfoZoneType() == InfoZoneType.anchorZone) {
            model.put("anchorZone", anchorWebService.findAnchorById(infoWeb.getZoneId()));
            model.put("pictureTopList", anchorWebService.pictureListTop(0, 6));
            model.put("likeInfoList", anchorWebService.likeInfoList(infoWeb, 10));
            return "/view/anchor/anchor-news-detail.html";
        }

        return "/view/news/news-detail.html";


    }

    @RequestMapping(value = "/news/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        model.put("infoList", new FindResultToMobile(infoWebService.infoListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        Tags tags = tagsService.findById(tagId);
        model.put("tags", tags);
        return "/view/news/news-tag-list.html";
    }

    /**
     * 标签新闻视频ajax请求
     *
     * @param infoRequest
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/news/tagList", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax tagListRest(@RequestBody InfoRequest infoRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        return new FindResultMoreToAjax(infoWebService.infoListByTagId(infoRequest.getTagId(), offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(infoRequest.getTemplateName()));
    }

}
