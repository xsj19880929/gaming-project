package com.ygccw.website.pc.anchor.controller;

import com.ygccw.website.pc.anchor.service.AnchorWebService;
import com.ygccw.wechat.common.zone.entity.AnchorZone;
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
public class AnchorController {
    @Inject
    private AnchorWebService anchorWebService;

    @RequestMapping(value = "/anchor.html", method = RequestMethod.GET)
    public String anchorList(final ModelMap model) {
        AnchorZone anchorZone = new AnchorZone();
        model.put("anchorZoneListNew", anchorWebService.findAnchorZoneNew(anchorZone, 0, 20));
        model.put("anchorZoneListTop", anchorWebService.findAnchorZoneTop(anchorZone, 0, 20));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", 0);

        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor/findPlatformId/{platformId}.html", method = RequestMethod.GET)
    public String selectAnchorList(final ModelMap model, @PathVariable Long platformId) {
        AnchorZone anchorZone = new AnchorZone();
        if (platformId != 0) {
            anchorZone.setPlatformId(platformId);
        }
        model.put("anchorZoneListNew", anchorWebService.findAnchorZoneNew(anchorZone, 0, 20));
        model.put("anchorZoneListTop", anchorWebService.findAnchorZoneTop(anchorZone, 0, 20));
        model.put("anchorZonePlatformList", anchorWebService.listAnchorZonePlatform());
        model.put("platformIdSelected", platformId);
        return "/view/anchor/anchor-list.html";
    }

    @RequestMapping(value = "/anchor/{id}.html", method = RequestMethod.GET)
    public String findById(final ModelMap model, @PathVariable Long id) {
        return "/view/anchor/anchor-index.html";
    }
}
