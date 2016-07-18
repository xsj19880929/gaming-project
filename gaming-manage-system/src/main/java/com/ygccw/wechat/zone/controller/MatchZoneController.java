package com.ygccw.wechat.zone.controller;

import com.ygccw.wechat.common.recommend.enums.RecommendType;
import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.entity.MatchZone;
import com.ygccw.wechat.common.zone.entity.MatchZoneArea;
import com.ygccw.wechat.common.zone.entity.MatchZoneYear;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import com.ygccw.wechat.common.zone.service.MatchZoneAreaService;
import com.ygccw.wechat.common.zone.service.MatchZoneService;
import com.ygccw.wechat.common.zone.service.MatchZoneYearService;
import com.ygccw.wechat.sys.Menu;
import com.ygccw.wechat.zone.model.MatchZoneModel;
import com.ygccw.wechat.zone.service.MatchZoneModelService;
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
    @Inject
    MatchTeamService matchTeamService;
    @Inject
    MatchZoneModelService matchZoneModelService;

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
        return matchZoneModelService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody MatchZoneModel matchZoneModel) {
        matchZoneModelService.update(matchZoneModel);
    }


    @RequireSession
    @RequestMapping(value = "/zone/match-zone/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        matchZoneService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody MatchZoneModel matchZoneModel) {
        matchZoneModelService.save(matchZoneModel);
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

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/match-team/list", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchTeam> listMatchTeam() {
        return matchTeamService.listAll();
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/recommend/list", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchTeam> listRecommend(@PathVariable("recommendType") RecommendType recommendType) {
        return matchTeamService.listAll();
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-zone/listAll", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchZone> listAll() {
        return matchZoneService.listAll();
    }


}
