package com.ygccw.website.interceptor;

import com.ygccw.wechat.common.advertising.enums.AdvType;
import com.ygccw.wechat.common.advertising.service.AdvertisingService;
import com.ygccw.wechat.common.hotkeywords.entity.HotKeywords;
import com.ygccw.wechat.common.hotkeywords.service.HotKeywordsService;
import core.framework.web.site.SiteSettings;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Inject
    SiteSettings siteSettings;
    @Inject
    HotKeywordsService hotKeywordsService;
    @Inject
    AdvertisingService advertisingService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("currentUrl", siteSettings.baseUrl() + request.getRequestURI());
        request.setAttribute("hotKeywordsList", hotKeywordsService.list(new HotKeywords(), 0, 10));
        request.setAttribute("siteName", "55电竞游戏网");
        request.setAttribute("baseUrl", siteSettings.baseUrl());
        request.setAttribute("newsAdvTop", advertisingService.findByAdvType(AdvType.top)); //头广告
    }
}
