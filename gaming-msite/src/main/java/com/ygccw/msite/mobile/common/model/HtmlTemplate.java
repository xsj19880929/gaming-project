package com.ygccw.msite.mobile.common.model;

import core.framework.web.site.SiteSettings;

import javax.inject.Inject;

/**
 * @author soldier
 */
public class HtmlTemplate {
    @Inject
    SiteSettings siteSettings;
    private final String baseUrl = siteSettings.baseUrl();
    private final String imageUrl = siteSettings.getImgServerUrl();
    private String htmlTemplate;

    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
