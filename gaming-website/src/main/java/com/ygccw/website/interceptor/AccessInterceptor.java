package com.ygccw.website.interceptor;

import core.framework.web.site.SiteSettings;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Inject
    SiteSettings siteSettings;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("currentUrl", siteSettings.baseUrl() + request.getRequestURI());
    }
}
