package com.ygccw.wechat.advertising.controller;

import com.ygccw.wechat.common.advertising.entity.Advertising;
import com.ygccw.wechat.common.advertising.service.AdvertisingService;
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
@Menu({"recommend.advertising.list"})
public class AdvertisingController {
    @Inject
    AdvertisingService advertisingService;

    @RequireSession
    @RequestMapping(value = "/advertising/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<Advertising> list(@RequestBody Advertising advertising, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(advertisingService.list(advertising, offset, fetchSize), offset, advertisingService.listSize(advertising));
    }

    @RequireSession
    @RequestMapping(value = "/advertising", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Advertising advertising) {
        advertisingService.update(advertising);
    }


    @RequireSession
    @RequestMapping(value = "/advertising/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        advertisingService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "/advertising", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Advertising advertising) {
        advertisingService.save(advertising);
    }

    @RequireSession
    @RequestMapping(value = "/advertising/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Advertising findById(@PathVariable("id") Long id) {
        return advertisingService.findById(id);
    }

}
