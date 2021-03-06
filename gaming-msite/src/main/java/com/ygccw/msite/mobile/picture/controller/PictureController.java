package com.ygccw.msite.mobile.picture.controller;

import com.ygccw.msite.database.FindResultMoreToAjax;
import com.ygccw.msite.database.FindResultToMobile;
import com.ygccw.msite.mobile.common.model.HtmlTemplate;
import com.ygccw.msite.mobile.common.service.AjaxGetTemplateService;
import com.ygccw.msite.mobile.picture.service.PictureWebService;
import com.ygccw.msite.utils.PageUtils;
import com.ygccw.wechat.common.picture.entity.Picture;
import com.ygccw.wechat.common.picture.service.PictureService;
import com.ygccw.wechat.common.tags.service.TagsService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Inject
    private AjaxGetTemplateService ajaxGetTemplateService;

    @RequestMapping(value = "/picture/", method = RequestMethod.GET)
    public String pictureList(final ModelMap model) {
        int fetchSize = 6;
        model.put("pictureList", new FindResultToMobile(pictureWebService.pictureList(0, fetchSize), fetchSize, ""));
        return "/view/picture/picture-list.html";
    }

    /**
     * 所有图片ajax请求
     *
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/picture/list", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax listRest(@RequestBody Picture picture, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        List<Picture> pictureList = pictureWebService.pictureListOutCondition(picture, offset, fetchSize);
        HtmlTemplate htmlTemplate = ajaxGetTemplateService.getHtmlTemplate("picture-list-template.html");
        return new FindResultMoreToAjax(pictureList, htmlTemplate);
    }

    @RequestMapping(value = "/picture_{currentPage}.html", method = RequestMethod.GET)
    public String pictureListPage(final ModelMap model, @PathVariable Integer currentPage) {
        int fetchSize = 6;
        model.put("pictureList", new FindResultToMobile(pictureWebService.pictureList(PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, ""));
        return "/view/picture/picture-list.html";
    }

    @RequestMapping(value = "/picture/{id}.html", method = RequestMethod.GET)
    public String findById(final ModelMap model, @PathVariable Long id) {
        Picture picture = pictureWebService.findById(id);
        model.put("picture", picture);
        pictureService.updateVisitCount(id); //点击
        return "/view/picture/picture-detail.html";
    }

    @RequestMapping(value = "/picture/tag/{tagId}_{currentPage}.html", method = RequestMethod.GET)
    public String tagList(HttpServletRequest request, final ModelMap model, @PathVariable Long tagId, @PathVariable Integer currentPage) {
        int fetchSize = 6;
        model.put("pictureList", new FindResultToMobile(pictureWebService.pictureListByTagId(tagId, PageUtils.getStartRecord(currentPage, fetchSize), fetchSize), fetchSize, PageUtils.getPageUrl(request)));
        model.put("tags", tagsService.findById(tagId));
        return "/view/picture/picture-tag-list.html";
    }

    /**
     * 所有图片标签ajax请求
     *
     * @param offset
     * @param fetchSize
     * @return
     */
    @RequestMapping(value = "/picture/tagList", method = RequestMethod.POST)
    @ResponseBody
    public FindResultMoreToAjax tagListRest(@RequestBody Picture picture, @RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "fetchSize", defaultValue = "20") int fetchSize) {
        List<Picture> pictureList = pictureWebService.pictureListByTagId(picture.getTagId(), offset, fetchSize);
        HtmlTemplate htmlTemplate = ajaxGetTemplateService.getHtmlTemplate(picture.getTemplateName());
        return new FindResultMoreToAjax(pictureList, htmlTemplate);
    }


}
