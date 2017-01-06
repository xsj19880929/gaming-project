package com.ygccw.wechat.hotkeywords.controller;

import com.ygccw.wechat.common.hotkeywords.entity.HotKeywords;
import com.ygccw.wechat.common.hotkeywords.service.HotKeywordsService;
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
@Menu({"recommend.hot-keywords.list"})
public class HotKeywordsController {
    @Inject
    HotKeywordsService hotKeywordsService;

    @RequireSession
    @RequestMapping(value = "/hotKeywords/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<HotKeywords> list(@RequestBody HotKeywords hotKeywords, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(hotKeywordsService.list(hotKeywords, offset, fetchSize), offset, hotKeywordsService.listSize(hotKeywords));
    }

    @RequireSession
    @RequestMapping(value = "/hotKeywords", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody HotKeywords hotKeywords) {
        hotKeywordsService.update(hotKeywords);
    }


    @RequireSession
    @RequestMapping(value = "/hotKeywords/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        hotKeywordsService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/hotKeywords", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody HotKeywords hotKeywords) {
        hotKeywordsService.save(hotKeywords);
    }

    @RequireSession
    @RequestMapping(value = "/hotKeywords/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HotKeywords findById(@PathVariable("id") Long id) {
        return hotKeywordsService.findById(id);
    }

}
