package com.ygccw.website.pc.picture.controller;

import com.ygccw.website.database.FindResultToSale;
import com.ygccw.website.pc.picture.service.PictureWebService;
import com.ygccw.website.utils.PageUtils;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.service.TagsService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author soldier
 */
@Controller
public class PictureController {
    @Inject
    PictureWebService pictureWebService;
    @Inject
    Environment env;
    @Inject
    TagsService tagsService;
    @Inject
    PictureService pictureService;

    @RequestMapping(value = "/picture/", method = RequestMethod.GET)
    public String pictureList(final ModelMap model) {
        int currentPage = 1;
        int fetchSize = 15;
        String url = "/picture";
        model.put("pictureList", new FindResultToSale(pictureWebService.pictureList(PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), pictureWebService.pictureListSize(), currentPage, fetchSize, url));
        model.put("tagList", pictureWebService.listHotTags());
        return "/view/picture/picture-list.html";
    }

    @RequestMapping(value = "/picture_{currentPage}.html", method = RequestMethod.GET)
    public String pictureListPage(HttpServletRequest request, final ModelMap model, @PathVariable Integer currentPage) {
        String url = PageUtils.getPageUrl(request);
        int fetchSize = 15;
        model.put("pictureList", new FindResultToSale(pictureWebService.pictureList(PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), pictureWebService.pictureListSize(), currentPage, fetchSize, url));
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
        pictureService.updateVisitCount(id); //点击
        return "/view/picture/picture-detail.html";
    }

    @RequestMapping(value = "/picture/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 20;
        model.put("pictureList", new FindResultToSale(pictureWebService.pictureListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), pictureWebService.pictureListByTagIdSize(tagId), currentPage, fetchSize, PageUtils.getPageUrl(request)));
        model.put("tags", tagsService.findById(tagId));
        return "/view/picture/picture-tag-list.html";
    }


}
