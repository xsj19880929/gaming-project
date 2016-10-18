package com.ygccw.website.pc.picture.controller;

import com.ygccw.website.pc.picture.service.PictureWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Controller
public class PictureController {
    @Inject
    PictureWebService pictureWebService;

    @RequestMapping(value = "/picture.html", method = RequestMethod.GET)
    public String pictureList(final ModelMap model) {
        model.put("pictureList", pictureWebService.pictureList(0, 15));
        model.put("tagList", pictureWebService.listHotTags());
        return "/view/picture/picture-list.html";
    }


}
