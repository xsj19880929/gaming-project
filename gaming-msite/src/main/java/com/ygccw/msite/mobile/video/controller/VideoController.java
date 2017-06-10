package com.ygccw.msite.mobile.video.controller;

import com.ygccw.msite.database.FindResultMoreToAjax;
import com.ygccw.msite.database.FindResultToMobile;
import com.ygccw.msite.mobile.anchor.service.AnchorWebService;
import com.ygccw.msite.mobile.common.model.HtmlTemplate;
import com.ygccw.msite.mobile.common.service.AjaxGetTemplateService;
import com.ygccw.msite.mobile.game.service.GameWebService;
import com.ygccw.msite.mobile.info.model.InfoWeb;
import com.ygccw.msite.mobile.video.model.VideoRequest;
import com.ygccw.msite.mobile.video.service.VideoWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoType;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import com.ygccw.wechat.common.info.service.InfoService;
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
import java.util.List;

/**
 * @author soldier
 */
@Controller
public class VideoController {
    @Inject
    VideoWebService videoWebService;
    @Inject
    TagsService tagsService;
    @Inject
    InfoService infoService;
    @Inject
    GameWebService gameWebService;
    @Inject
    AnchorWebService anchorWebService;
    @Inject
    AjaxGetTemplateService ajaxGetTemplateService;

    @RequestMapping(value = "/video/", method = RequestMethod.GET)
    public String videoList(final ModelMap model) {
        int fetchSize = 10;
        model.put("pageFlag", "new");
        model.put("anchorZoneList", videoWebService.anchorList(0, 30));
        model.put("matchZoneList", videoWebService.matchZoneList(0, 30));
        model.put("videoList", new FindResultToMobile(videoWebService.videoList(new Info(), 0, fetchSize), fetchSize, "publishTime"));
        model.put("infoZoneTypeSelected", "all");
        model.put("infoVideoTypeSelected", "all");
        model.put("zoneIdSelected", 0);
        return "/view/video/video-list.html";
    }

    /**
     * 所有视频加载更多
     *
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/video/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax listRest(@RequestBody VideoRequest videoRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        Info info = commonAjax(videoRequest.getInfoVideoTypeStr(), videoRequest.getInfoZoneTypeStr(), videoRequest.getZoneId());
        info.setVerify(1);
        info.setInfoType(InfoType.video);
        info.setSortIfDesc(videoRequest.getSortIfDesc());
        info.setSortName(videoRequest.getSortName());
        List<Info> infoList = infoService.list(info, offset, fetchSize);
        HtmlTemplate htmlTemplate = ajaxGetTemplateService.getHtmlTemplate(videoRequest.getTemplateName());
        return new FindResultMoreToAjax(infoList, htmlTemplate);
    }

    @RequestMapping(value = "/video_new/{infoVideoTypeStr}/{infoZoneTypeStr}/{zoneId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectVideoList(final ModelMap model, @PathVariable String infoVideoTypeStr, @PathVariable String infoZoneTypeStr, @PathVariable Long zoneId, @PathVariable Integer currentPage) {
        Info info = common(model, infoVideoTypeStr, infoZoneTypeStr, zoneId);
        int fetchSize = 10;
        model.put("pageFlag", "new");
        model.put("videoList", new FindResultToMobile(videoWebService.videoList(info, 0, fetchSize), fetchSize, "publishTime"));
        return "/view/video/video-list.html";
    }

    @RequestMapping(value = "/video_top/{infoVideoTypeStr}/{infoZoneTypeStr}/{zoneId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectVideoListTop(final ModelMap model, @PathVariable String infoVideoTypeStr, @PathVariable String infoZoneTypeStr, @PathVariable Long zoneId, @PathVariable Integer currentPage) {
        Info info = common(model, infoVideoTypeStr, infoZoneTypeStr, zoneId);
        int fetchSize = 10;
        model.put("pageFlag", "top");
        model.put("videoList", new FindResultToMobile(videoWebService.videoListTop(info, 0, fetchSize), fetchSize, "visitCount"));
        return "/view/video/video-list.html";
    }

    private Info common(ModelMap model, String infoVideoTypeStr, String infoZoneTypeStr, Long zoneId) {
        Info info = new Info();
        if (!"all".equals(infoVideoTypeStr)) {
            for (InfoVideoType infoVideoType : InfoVideoType.values()) {
                if (infoVideoType.getName().equals(infoVideoTypeStr)) {
                    info.setInfoVideoType(infoVideoType);
                    model.put("infoVideoTypeSelected", infoVideoTypeStr);
                }
            }
        } else {
            model.put("infoVideoTypeSelected", "all");
        }

        if (!"all".equals(infoZoneTypeStr)) {
            for (InfoZoneType infoZoneType : InfoZoneType.values()) {
                if (infoZoneType.getName().equals(infoZoneTypeStr)) {
                    info.setInfoZoneType(infoZoneType);
                    model.put("infoZoneTypeSelected", infoZoneTypeStr);
                }
            }
        } else {
            model.put("infoZoneTypeSelected", "all");
        }
        if (zoneId.compareTo(0L) != 0) {
            info.setZoneId(zoneId);
        }
        model.put("zoneIdSelected", zoneId);
        model.put("anchorZoneList", videoWebService.anchorList(0, 20));
        model.put("matchZoneList", videoWebService.matchZoneList(0, 20));
        return info;
    }

    private Info commonAjax(String infoVideoTypeStr, String infoZoneTypeStr, Long zoneId) {
        Info info = new Info();
        if (!"all".equals(infoVideoTypeStr)) {
            for (InfoVideoType infoVideoType : InfoVideoType.values()) {
                if (infoVideoType.getName().equals(infoVideoTypeStr)) {
                    info.setInfoVideoType(infoVideoType);
                }
            }
        }

        if (!"all".equals(infoZoneTypeStr)) {
            for (InfoZoneType infoZoneType : InfoZoneType.values()) {
                if (infoZoneType.getName().equals(infoZoneTypeStr)) {
                    info.setInfoZoneType(infoZoneType);
                }
            }
        }
        if (zoneId.compareTo(0L) != 0) {
            info.setZoneId(zoneId);
        }
        return info;
    }

    @RequestMapping(value = "/video/{id}.html", method = RequestMethod.GET)
    public String videoList(final ModelMap model, @PathVariable Long id) {
        InfoWeb infoWeb = videoWebService.findById(id);
        infoService.updateVisitCount(id);
        if (infoWeb.getInfoZoneType() == InfoZoneType.trade) {
            model.put("video", infoWeb);
            model.put("likeInfoList", videoWebService.likeInfoList(infoWeb, 10));
            return "/view/video/video-detail.html";
        } else if (infoWeb.getInfoZoneType() == InfoZoneType.matchZone) {
            model.put("info", infoWeb);
            model.put("pictureTopList", gameWebService.pictureListTop(0, 6));
            model.put("likeInfoList", gameWebService.likeInfoList(infoWeb, 10));
            model.put("matchZone", gameWebService.findById(infoWeb.getZoneId()));
            return "/view/game/game-video-detail.html";
        } else if (infoWeb.getInfoZoneType() == InfoZoneType.anchorZone) {
            model.put("info", infoWeb);
            model.put("anchorZone", anchorWebService.findAnchorById(infoWeb.getZoneId()));
            model.put("pictureTopList", anchorWebService.pictureListTop(0, 6));
            model.put("likeInfoList", anchorWebService.likeInfoList(infoWeb, 10));
            return "/view/anchor/anchor-video-detail.html";
        }
        return "/view/video/video-detail.html";
    }

    @RequestMapping(value = "/video/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 10;
        model.put("videoList", new FindResultToMobile(videoWebService.videoListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        model.put("tags", tagsService.findById(tagId));
        return "/view/video/video-tag-list.html";
    }

    /**
     * 所有图片标签ajax请求
     *
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/video/tagList", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax tagListRest(@RequestBody VideoRequest videoRequest, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        List<Info> videoList = videoWebService.videoListByTagId(videoRequest.getTagId(), offset, fetchSize);
        HtmlTemplate htmlTemplate = ajaxGetTemplateService.getHtmlTemplate(videoRequest.getTemplateName());
        return new FindResultMoreToAjax(videoList, htmlTemplate);
    }

}
