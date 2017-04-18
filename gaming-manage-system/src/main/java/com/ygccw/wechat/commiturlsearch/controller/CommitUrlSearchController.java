package com.ygccw.wechat.commiturlsearch.controller;

import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.service.CommitUrlSearchService;
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
@Menu({"site.commit-url-search.list"})
public class CommitUrlSearchController {
    @Inject
    CommitUrlSearchService commitUrlSearchService;

    @RequireSession
    @RequestMapping(value = "/commitUrlSearch/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<CommitUrlSearch> list(@RequestBody CommitUrlSearch commitUrlSearch, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(commitUrlSearchService.list(commitUrlSearch, offset, fetchSize), offset, commitUrlSearchService.listSize(commitUrlSearch));
    }

    @RequireSession
    @RequestMapping(value = "/commitUrlSearch", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody CommitUrlSearch commitUrlSearch) {
        commitUrlSearchService.update(commitUrlSearch);
    }


    @RequireSession
    @RequestMapping(value = "/commitUrlSearch/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        commitUrlSearchService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/commitUrlSearch", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody CommitUrlSearch commitUrlSearch) {
        commitUrlSearchService.save(commitUrlSearch);
    }

    @RequireSession
    @RequestMapping(value = "/commitUrlSearch/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommitUrlSearch findById(@PathVariable("id") Long id) {
        return commitUrlSearchService.findById(id);
    }

}
