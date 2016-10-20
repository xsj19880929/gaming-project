package com.ygccw.website.pc.video.controller;

import com.ygccw.website.pc.info.model.InfoWeb;
import com.ygccw.website.pc.video.service.VideoWebService;
import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.enums.InfoVideoType;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Controller
public class VideoController {
    @Inject
    VideoWebService videoWebService;

    @RequestMapping(value = "/video.html", method = RequestMethod.GET)
    public String videoList(final ModelMap model) {
        Info info = new Info();
        model.put("anchorZoneList", videoWebService.anchorList(0, 8));
        model.put("matchZoneList", videoWebService.matchZoneList(0, 8));
        model.put("anchorZoneListMore", videoWebService.anchorList(8, 100));
        model.put("matchZoneListMore", videoWebService.matchZoneList(8, 100));
        model.put("videoListNew", videoWebService.videoList(info, 0, 16));
        model.put("videoListTop", videoWebService.videoListTop(info, 0, 16));
        model.put("infoZoneTypeSelected", "all");
        model.put("infoVideoTypeSelected", "all");
        model.put("zoneIdSelected", 0);
        return "/view/video/video-list.html";
    }

    @RequestMapping(value = "/video/{infoVideoTypeStr}/{infoZoneTypeStr}/{zoneId}.html", method = RequestMethod.GET)
    public String selectVideoList(final ModelMap model, @PathVariable String infoVideoTypeStr, @PathVariable String infoZoneTypeStr, @PathVariable Long zoneId) {
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
        model.put("videoListNew", videoWebService.videoList(info, 0, 16));
        model.put("videoListTop", videoWebService.videoListTop(info, 0, 16));
        return "/view/video/video-list.html";
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
}
