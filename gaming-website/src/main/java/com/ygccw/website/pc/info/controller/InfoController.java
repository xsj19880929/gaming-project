package com.ygccw.website.pc.info.controller;

import com.ygccw.website.pc.info.service.InfoWebService;
import com.ygccw.wechat.common.info.enums.InfoZoneType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Controller
public class InfoController {
    @Inject
    private InfoWebService infoWebService;

    @RequestMapping(value = "/news.html", method = RequestMethod.GET)
    public String index(final ModelMap model) {
        model.put("infoAllList", infoWebService.infoList(null, 0, 9));
        model.put("infoTradeList", infoWebService.infoList(InfoZoneType.trade, 0, 9));
        model.put("infoMatchList", infoWebService.infoList(InfoZoneType.matchZone, 0, 9));
        model.put("infoAnchorList", infoWebService.infoList(InfoZoneType.anchorZone, 0, 9));
        return "/view/news/news-list.html";
    }
}
