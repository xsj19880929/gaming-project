package com.ygccw.website.pc.picture.controller;

import com.ygccw.website.pc.picture.service.PictureWebService;
import com.ygccw.wechat.common.picture.entity.Picture;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Inject
    Environment env;

    @RequestMapping(value = "/picture.html", method = RequestMethod.GET)
    public String pictureList(final ModelMap model) {
        model.put("pictureList", pictureWebService.pictureList(0, 15));
        model.put("tagList", pictureWebService.listHotTags());
        return "/view/picture/picture-list.html";
    }

    @RequestMapping(value = "/picture/{id}.html", method = RequestMethod.GET)
    public String findById(final ModelMap model, @PathVariable Long id) {
        Picture picture = pictureWebService.findById(id);
        model.put("picture", picture);
        model.put("pictureTopList", pictureWebService.pictureListTop(0, 10));
        model.put("pictureLikeList", pictureWebService.likePictureList(picture, 5));
        model.put("imageServer", env.getProperty("out.image.downloadUrl"));
        return "/view/picture/picture-detail.html";
    }


}
