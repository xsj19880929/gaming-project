package com.ygccw.wechat.zone.controller;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
import com.ygccw.wechat.sys.Menu;
import com.ygccw.wechat.zone.model.MatchTeamMappingModel;
import com.ygccw.wechat.zone.model.MatchTeamModel;
import com.ygccw.wechat.zone.service.MatchTeamModelService;
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
@Menu({"zone.match-team.list"})
public class MatchTeamController {
    @Inject
    MatchTeamService matchTeamService;
    @Inject
    MatchTeamModelService matchTeamModelService;

    @RequireSession
    @RequestMapping(value = "/zone/match-team/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<MatchTeam> list(@RequestBody MatchTeam matchTeam, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(matchTeamService.list(matchTeam, offset, fetchSize), offset, matchTeamService.listSize(matchTeam));
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MatchTeamModel findOne(@PathVariable("id") Long id) {
        return matchTeamModelService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody MatchTeamModel matchTeamModel) {
        matchTeamModelService.update(matchTeamModel);
    }


    @RequireSession
    @RequestMapping(value = "/zone/match-team/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        matchTeamService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody MatchTeamModel matchTeamModel) {
        matchTeamModelService.save(matchTeamModel);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team/match-team-mapping-list", method = RequestMethod.GET)
    @ResponseBody
    public List<MatchTeamMappingModel> matchTeamMappingList() {
        return matchTeamModelService.listMatchTeamMappingModel();
    }

}
