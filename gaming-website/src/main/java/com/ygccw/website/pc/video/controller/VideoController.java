package com.ygccw.website.pc.video.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.video.service.VideoWebService;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
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
public class VideoController {
    @Inject
    VideoWebService videoWebService;
    @Inject
    TagsService tagsService;

    @RequestMapping(value = "/video.html", method = RequestMethod.GET)
    public String videoList(final ModelMap model) {
        int currentPage = 1;
        int fetchSize = 16;
        Info info = new Info();
        String url = "/video_new/all/all/0";
        model.put("pageFlag", "new");
        model.put("anchorZoneList", videoWebService.anchorList(0, 8));
        model.put("matchZoneList", videoWebService.matchZoneList(0, 8));
        model.put("anchorZoneListMore", videoWebService.anchorList(8, 100));
        model.put("matchZoneListMore", videoWebService.matchZoneList(8, 100));
        model.put("videoList", new FindResultToSale(videoWebService.videoList(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), videoWebService.videoListSize(info), currentPage, fetchSize, url));
        model.put("infoZoneTypeSelected", "all");
        model.put("infoVideoTypeSelected", "all");
        model.put("zoneIdSelected", 0);
        return "/view/video/video-list.html";
    }

    @RequestMapping(value = "/video_new/{infoVideoTypeStr}/{infoZoneTypeStr}/{zoneId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectVideoList(final ModelMap model, @PathVariable String infoVideoTypeStr, @PathVariable String infoZoneTypeStr, @PathVariable Long zoneId, @PathVariable Integer currentPage) {
        Info info = common(model, infoVideoTypeStr, infoZoneTypeStr, zoneId);
        int fetchSize = 16;
        String url = "/video_new/" + infoVideoTypeStr + "/" + infoZoneTypeStr + "/" + zoneId;
        model.put("pageFlag", "new");
        model.put("videoList", new FindResultToSale(videoWebService.videoList(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), videoWebService.videoListSize(info), currentPage, fetchSize, url));
        return "/view/video/video-list.html";
    }

    @RequestMapping(value = "/video_top/{infoVideoTypeStr}/{infoZoneTypeStr}/{zoneId}_{currentPage}.html", method = RequestMethod.GET)
    public String selectVideoListTop(final ModelMap model, @PathVariable String infoVideoTypeStr, @PathVariable String infoZoneTypeStr, @PathVariable Long zoneId, @PathVariable Integer currentPage) {
        Info info = common(model, infoVideoTypeStr, infoZoneTypeStr, zoneId);
        int fetchSize = 16;
        String url = "/video_top/" + infoVideoTypeStr + "/" + infoZoneTypeStr + "/" + zoneId;
        model.put("pageFlag", "top");
        model.put("videoList", new FindResultToSale(videoWebService.videoListTop(info, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), videoWebService.videoListTopSize(info), currentPage, fetchSize, url));
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
        model.put("anchorZoneList", videoWebService.anchorList(0, 8));
        model.put("matchZoneList", videoWebService.matchZoneList(0, 8));
        model.put("anchorZoneListMore", videoWebService.anchorList(8, 100));
        model.put("matchZoneListMore", videoWebService.matchZoneList(8, 100));
        return info;
    }

    @RequestMapping(value = "/video/{id}.html", method = RequestMethod.GET)
    public String videoList(final ModelMap model, @PathVariable Long id) {
        InfoWeb infoWeb = videoWebService.findById(id);
        model.put("video", infoWeb);
        model.put("videoTopList", videoWebService.videoListTop(new Info(), 0, 10));
        model.put("anchorTopList", videoWebService.anchorListTop(0, 6));
        model.put("matchZoneTopList", videoWebService.findTopMatchZoneList(0, 10));
        model.put("nextVideo", videoWebService.nextInfo(infoWeb));
        model.put("lastVideo", videoWebService.lastInfo(infoWeb));
        return "/view/video/video-detail.html";
    }

    @RequestMapping(value = "/video/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 16;
        model.put("anchorZoneList", videoWebService.anchorList(0, 8));
        model.put("matchZoneList", videoWebService.matchZoneList(0, 8));
        model.put("anchorZoneListMore", videoWebService.anchorList(8, 100));
        model.put("matchZoneListMore", videoWebService.matchZoneList(8, 100));
        model.put("videoList", new FindResultToSale(videoWebService.videoListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), videoWebService.videoListByTagIdSize(tagId), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        model.put("tag", tagsService.findById(tagId));
        return "/view/video/video-tag-list.html";
    }
}
