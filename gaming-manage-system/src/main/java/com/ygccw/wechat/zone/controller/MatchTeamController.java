package com.ygccw.wechat.zone.controller;

import com.ygccw.wechat.common.zone.entity.MatchTeam;
import com.ygccw.wechat.common.zone.service.MatchTeamService;
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

/**
 * @author soldier
 */
@RestController
@Menu({"zone.match-team.list"})
public class MatchTeamController {
    @Inject
    MatchTeamService matchTeamService;

    @RequireSession
    @RequestMapping(value = "/zone/match-team/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<MatchTeam> list(@RequestBody MatchTeam matchTeam, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(matchTeamService.list(matchTeam, offset, fetchSize), offset, matchTeamService.listSize(matchTeam));
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MatchTeam findOne(@PathVariable("id") Long id) {
        return matchTeamService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody MatchTeam matchTeam) {
        matchTeamService.update(matchTeam);
    }


    @RequireSession
    @RequestMapping(value = "/zone/match-team/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void del(@PathVariable("id") Long id) {
        matchTeamService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/zone/match-team", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody MatchTeam matchTeam) {
        matchTeamService.save(matchTeam);
    }


}
