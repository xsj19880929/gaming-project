package com.ygccw.website.utils;

/**
 * Created by lucian.lin on 16/5/5.
 */
public class HttpUtil {

//    /**
//     * 判断是否ajax请求
//     *
//     * @param request
//     * @return
//     */
//    public static boolean isAjaxRequest(HttpServletRequest request) {
//        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
//    }
//
//    /**
//     * 判断是否来自微信浏览器
//     *
//     * @param request
//     * @return
//     */
//    public static boolean isWechatRequest(HttpServletRequest request) {
//        String header = request.getHeader("user-agent");
//        return header != null && (header.matches(".*MicroMessenger.*") || header.matches(".*MQQBrowser.*"));
//    }

    public static void main(String[] args) {
        int i = 5;
        int j = 10;
        System.out.println((i / 10) + ((i % 10) != 0 ? 1 : 0));
    }


}
