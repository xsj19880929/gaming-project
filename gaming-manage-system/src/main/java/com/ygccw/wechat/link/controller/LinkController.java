package com.ygccw.wechat.link.controller;

import com.ygccw.wechat.common.link.entity.Link;
import com.ygccw.wechat.common.link.service.LinkService;
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
@Menu({"recommend.link.list"})
public class LinkController {
    @Inject
    LinkService linkService;

    @RequireSession
    @RequestMapping(value = "/link/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<Link> list(@RequestBody Link link, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(linkService.list(link, offset, fetchSize), offset, linkService.listSize(link));
    }

    @RequireSession
    @RequestMapping(value = "/link", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Link link) {
        linkService.update(link);
    }


    @RequireSession
    @RequestMapping(value = "/link/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        linkService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/link", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Link link) {
        linkService.save(link);
    }

    @RequireSession
    @RequestMapping(value = "/link/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Link findById(@PathVariable("id") Long id) {
        return linkService.findById(id);
    }

}
