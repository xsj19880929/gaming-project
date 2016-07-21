package com.ygccw.wechat.info.controller;

import com.ygccw.wechat.common.info.entity.Info;
import com.ygccw.wechat.common.info.service.InfoService;
import com.ygccw.wechat.info.model.InfoModel;
import com.ygccw.wechat.info.service.InfoModelService;
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
@Menu({"info.list"})
public class InfoController {
    @Inject
    InfoService infoService;
    @Inject
    InfoModelService infoModelService;

    @RequireSession
    @RequestMapping(value = "info/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<InfoModel> list(@RequestBody Info info, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(infoModelService.list(info, offset, fetchSize), offset, infoService.listSize(info));
    }

    @RequireSession
    @RequestMapping(value = "info/{id}", method = RequestMethod.GET)
    @ResponseBody
    public InfoModel findOne(@PathVariable("id") Long id) {
        return infoModelService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "info", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody InfoModel infoModel) {
        infoModelService.update(infoModel);
    }


    @RequireSession
    @RequestMapping(value = "info/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        infoService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "info", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody InfoModel infoModel) {
        infoModelService.save(infoModel);
    }


}
