package com.ygccw.wechat.utils;

import core.framework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author miller
 */
public class HttpUtil {
    public static String requestUriWithParam(String requestUri, Map<String, String[]> params) {
        StringBuilder b = new StringBuilder(requestUri);
        b.append('?');
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            b.append(entry.getKey())
                .append('=')
                .append(entry.getValue()[0])
                .append('&');
        }
        b.deleteCharAt(b.length() - 1);
        return b.toString();
    }

    /**
     * 获取客户端真实ip
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("x-forwarded-for");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasText(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                try {
                    //根据网卡取本机配置的IP
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(',') > 0) { //"***.***.***.***".length() = 15
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(','));
        }

        if (ipAddress != null) {
            ipAddress = ipAddress.trim();
        }
        return ipAddress;
    }
}
