package com.ygccw.website.pc.index.controller;

import com.ygccw.website.pc.index.service.IndexWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Controller
public class IndexController {
    @Inject
    private IndexWebService indexWebService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(final ModelMap model) {
        model.put("matchZoneList", indexWebService.findRecommendMatchZone());
        return "/index.html";
    }
}
