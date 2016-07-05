package com.ygccw.wechat.sys.controller;

import core.framework.web.site.session.RequireSession;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mandy.huang on 2016/5/11
 */
@RestController
public class UtilsRESTController {
    @Inject
    Environment env;

    //mobile地址
    @RequireSession
    @RequestMapping(value = "/mobilePath", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> mobilePath(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobilePath", "http://m.dev.yaxia.com/");
        return map;
    }
}
