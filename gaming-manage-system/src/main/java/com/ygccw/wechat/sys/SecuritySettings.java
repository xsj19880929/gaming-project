package com.ygccw.wechat.sys;

import java.util.HashSet;
import java.util.Set;

/**
 * @author nick.guo
 */
public class SecuritySettings {
    public static final String URL_SUFFIX = "/*";
    private final Set<String> excludeUrls = new HashSet<>();
    private String unauthenticatedUrl;
    private String unauthorizedUrl;
    private SecurityLevel securityLevel = SecurityLevel.LOGIN_REQUIRED;

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getUnauthenticatedUrl() {
        return unauthenticatedUrl;
    }

    public void setUnauthenticatedUrl(String unauthenticatedUrl) {
        this.unauthenticatedUrl = unauthenticatedUrl;
    }

    public SecurityLevel getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
    }

    public void addExcludeUrl(String url) {
        excludeUrls.add(url);
    }

    public boolean isExcludeUrl(String url) {
        for (String excludeUrl : excludeUrls) {
            if (excludeUrl.endsWith(URL_SUFFIX)) {
                String urlStart = excludeUrl.substring(0, excludeUrl.indexOf(URL_SUFFIX) + 1);
                if (url.startsWith(urlStart)) return true;
            }
            if (excludeUrl.equals(url)) return true;
        }

        return false;
    }

    public String getLogout() {
        return "/";
    }

    public String getLoginUrl() {
        return "/login";
    }

    protected String urlConcat(String... urls) {
        StringBuilder b = new StringBuilder();
        for (String url : urls) {
            if (url.endsWith("/")) {
                b.append(url.substring(0, url.length() - 1));
            } else {
                b.append(url);
            }
        }
        return b.toString();
    }

    public enum SecurityLevel {
        LOGIN_REQUIRED, ANONYMOUS
    }
}
