package com.ygccw.msite.mobile.index.controller;

import com.ygccw.msite.database.FindResultMoreToAjax;
import com.ygccw.msite.database.FindResultToMobile;
import com.ygccw.msite.mobile.common.service.AjaxGetTemplateService;
import com.ygccw.msite.mobile.index.service.IndexWebService;
import com.ygccw.msite.mobile.info.model.SearchRequest;
import com.ygccw.msite.mobile.info.service.InfoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
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

/**
 * @author soldier
 */
@Controller
public class IndexController {
    @Inject
    private IndexWebService indexWebService;
    @Inject
    private InfoWebService infoWebService;
    @Inject
    private AjaxGetTemplateService ajaxGetTemplateService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(final ModelMap model) {
        model.put("advImageList", indexWebService.findAdvertising(5));
        model.put("matchZoneNewList", indexWebService.findMatchZone());
        model.put("infoNewestList", indexWebService.findNewestInfoByInfoType(InfoType.news, 5));
        model.put("anchorZoneList", indexWebService.findAnchorZone());
        model.put("matchZoneVideoList", indexWebService.findMatchZoneVideo());
        model.put("infoMatchZoneVideoListAll", indexWebService.findMatchZoneVideoVideoInfoList(4));
        model.put("infoAnchorZoneVideoListAll", indexWebService.findAnchorZoneVideoVideoInfoList(4));
        model.put("anchorZoneVideoList", indexWebService.findAnchorZoneVideo());
        model.put("infoPlayerVideoInfoList", indexWebService.findVideoInfoList(InfoVideoType.playerVideo));
        model.put("matchZoneTopList", indexWebService.findTopMatchZoneList());
        model.put("pictureList", indexWebService.findNewestPictureList());
        model.put("recommendInfoList", indexWebService.findRecommendInfo(2));
        model.put("linkList", indexWebService.listLink(10));
        return "/index.html";
    }

    @RequestMapping(value = "/search_{keywords}_{type}_{currentPage}.html", method = RequestMethod.GET)
    public String search(HttpServletRequest request, final ModelMap model, @PathVariable String keywords, @PathVariable String type, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        if ("all".equals(type)) {
            fetchSize = 6;
            model.put("infoList", new FindResultToMobile(indexWebService.searchInfo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
            model.put("matchZoneList", new FindResultToMobile(indexWebService.searchMatchZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
            model.put("anchorZoneList", new FindResultToMobile(indexWebService.searchAnchorZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
            model.put("videoList", new FindResultToMobile(indexWebService.searchVideo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
            model.put("pictureList", new FindResultToMobile(indexWebService.searchPicture(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        } else if ("news".equals(type)) {
            model.put("templateName", "search-news-list-template.html");
            model.put("infoList", new FindResultToMobile(indexWebService.searchInfo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        } else if ("video".equals(type)) {
            model.put("templateName", "search-video-list-template.html");
            model.put("videoList", new FindResultToMobile(indexWebService.searchVideo(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        } else if ("match".equals(type)) {
            model.put("templateName", "search-match-list-template.html");
            model.put("matchZoneList", new FindResultToMobile(indexWebService.searchMatchZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        } else if ("anchor".equals(type)) {
            model.put("templateName", "search-anchor-list-template.html");
            model.put("anchorZoneList", new FindResultToMobile(indexWebService.searchAnchorZone(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        } else if ("picture".equals(type)) {
            model.put("templateName", "search-picture-list-template.html");
            model.put("pictureList", new FindResultToMobile(indexWebService.searchPicture(keywords, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        }
        model.put("newsTopList", infoWebService.newsListTop(0, 10));
        model.put("anchorTopList", infoWebService.anchorListTop(0, 6));
        model.put("videoTopList", infoWebService.videoListTop(0, 4));
        model.put("type", type);
        model.put("keywords", keywords);
        model.put("fetchSize", fetchSize);
        return "/search.html";
    }

    @RequestMapping(value = "/sitemap.html", method = RequestMethod.GET)
    public String siteMap(final ModelMap model) {
        model.put("matchZoneList", indexWebService.findSiteMapMatchZone(0, 30));
        model.put("tagList", indexWebService.listSiteMapHotTags(0, 10));
        model.put("newsList", indexWebService.findSiteMapNewestInfo(InfoType.news, 0, 10));
        model.put("videoList", indexWebService.findSiteMapNewestInfo(InfoType.video, 0, 10));
        model.put("matchZoneNewestList", indexWebService.findSiteMapNewestMatchZone(0, 10));
        model.put("anchorZoneNewestList", indexWebService.findSiteMapNewestAnchorZone(0, 10));
        model.put("pictureNewestList", indexWebService.findSiteMapNewestPicture(0, 10));
        return "/sitemap.html";
    }

    @RequestMapping(value = "/search.html", method = RequestMethod.GET)
    public String search(final ModelMap model) {
        return "/search.html";
    }

    /**
     * 搜索ajax请求
     *
     * @param searchRequest
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/search/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax listRest(@RequestBody SearchRequest searchRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        String type = searchRequest.getType();
        if ("news".equals(type)) {
            return new FindResultMoreToAjax(indexWebService.searchInfo(searchRequest.getKeywords(), offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(searchRequest.getTemplateName()));
        } else if ("video".equals(type)) {
            return new FindResultMoreToAjax(indexWebService.searchVideo(searchRequest.getKeywords(), offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(searchRequest.getTemplateName()));

        } else if ("match".equals(type)) {
            return new FindResultMoreToAjax(indexWebService.searchMatchZone(searchRequest.getKeywords(), offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(searchRequest.getTemplateName()));

        } else if ("anchor".equals(type)) {
            return new FindResultMoreToAjax(indexWebService.searchAnchorZone(searchRequest.getKeywords(), offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(searchRequest.getTemplateName()));

        } else if ("picture".equals(type)) {
            return new FindResultMoreToAjax(indexWebService.searchPicture(searchRequest.getKeywords(), offset, fetchSize), ajaxGetTemplateService.getHtmlTemplate(searchRequest.getTemplateName()));

        }
        return null;
    }

    /**
     * session保存
     *
     * @return
     */
    @RequestMapping(value = "/session/save/{key}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public void listRest(@PathVariable String key, @PathVariable String value) {

    }
}
