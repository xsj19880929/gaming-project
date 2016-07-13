package com.ygccw.wechat.zone.controller;

import com.ygccw.wechat.common.zone.entity.AnchorZone;
import com.ygccw.wechat.common.zone.entity.AnchorZonePlatform;
import com.ygccw.wechat.common.zone.service.AnchorZonePlatformService;
import com.ygccw.wechat.common.zone.service.AnchorZoneService;
import com.ygccw.wechat.sys.Menu;
import com.ygccw.wechat.zone.model.AnchorZoneMatchZoneMappingModel;
import com.ygccw.wechat.zone.model.AnchorZoneModel;
import com.ygccw.wechat.zone.service.AnchorZoneModelService;
import core.framework.database.FindResult;
import core.framework.web.site.session.RequireSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author soldier
 */
@RestController
@Menu({"zone.anchor-zone.list"})
public class AnchorZoneController {
    @Inject
    AnchorZoneService anchorZoneService;
    @Inject
    AnchorZonePlatformService anchorZonePlatformService;
    @Inject
    AnchorZoneModelService anchorZoneModelService;

    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<AnchorZone> list(@RequestBody AnchorZone anchorZone, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(anchorZoneService.list(anchorZone, offset, fetchSize), offset, anchorZoneService.listSize(anchorZone));
    }

    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AnchorZone findOne(@PathVariable("id") Long id) {
        return anchorZoneModelService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody AnchorZoneModel anchorZoneModel) {
        anchorZoneModelService.update(anchorZoneModel);
    }


    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        anchorZoneService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody AnchorZoneModel anchorZoneModel) {
        anchorZoneModelService.save(anchorZoneModel);
    }

    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone/anchor-zone-platform/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AnchorZonePlatform> listAnchorZonePlatformAll() {
        return anchorZonePlatformService.listAll();
    }

    @RequireSession
    @RequestMapping(value = "/zone/anchor-zone/match-zone/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AnchorZoneMatchZoneMappingModel> listMatchZoneAll() {
        return anchorZoneModelService.listAnchorZoneMatchZoneMappingModel();
    }


}
