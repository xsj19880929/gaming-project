package com.ygccw.wechat.picture.controller;

import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
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
@Menu({"picture.list"})
public class PictureController {
    @Inject
    PictureService pictureService;

    @RequireSession
    @RequestMapping(value = "picture/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResult<Picture> list(@RequestBody Picture picture, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "25") int fetchSize) {
        return new FindResult<>(pictureService.list(picture, offset, fetchSize), offset, pictureService.listSize(picture));
    }

    @RequireSession
    @RequestMapping(value = "picture/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Picture findOne(@PathVariable("id") Long id) {
        return pictureService.findById(id);
    }

    @RequireSession
    @RequestMapping(value = "picture", method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Picture picture) {
        pictureService.update(picture);
    }


    @RequireSession
    @RequestMapping(value = "picture/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        pictureService.deleteStatus(id);
    }

    @RequireSession
    @RequestMapping(value = "picture", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody Picture picture) {
        pictureService.save(picture);
    }


}
