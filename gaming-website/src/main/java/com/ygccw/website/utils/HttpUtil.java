package com.ygccw.website.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lucian.lin on 16/5/5.
 */
public class HttpUtil {

    /**
     * 判断是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 判断是否来自微信浏览器
     *
     * @param request
     * @return
     */
    public static boolean isWechatRequest(HttpServletRequest request) {
        String header = request.getHeader("user-agent");
        return header != null && (header.matches(".*MicroMessenger.*") || header.matches(".*MQQBrowser.*"));
    }


}
