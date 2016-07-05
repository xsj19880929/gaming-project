package com.ygccw.wechat.zone.controller;

import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneArea;
import com.ygccw.wechat.common.zone.entity.MatchZoneYear;
import com.ygccw.wechat.common.zone.service.MatchZoneAreaService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneYearService;
import com.ygccw.wechat.sys.Menu;
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
@Menu({"zone.match-zone.list"})
public class MatchZoneController {
    @Inject
    MatchZoneService matchZoneService;
    @Inject
    MatchZoneAreaService matchZoneAreaService;
    @Inject
    MatchZoneYearService matchZoneYearService;

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<MatchZone> list(@RequestBody MatchZone matchZone, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(matchZoneService.list(matchZone, offset, fetchSize), offset, matchZoneService.listSize(matchZone));
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MatchZone findOne(@PathVariable("id") Long id) {
        return matchZoneService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody MatchZone matchZone) {
        matchZoneService.update(matchZone);
    }


    @RequireSession
    @RequestMapping(value = "/zone/match-zone/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void del(@PathVariable("id") Long id) {
        matchZoneService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody MatchZone matchZone) {
        matchZoneService.save(matchZone);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/match-zone-area/list", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchZoneArea> listMatchZoneAreaAll() {
        return matchZoneAreaService.listAll();
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/match-zone-year/list", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchZoneYear> listMatchZoneYearAll() {
        return matchZoneYearService.listAll();
    }


}
