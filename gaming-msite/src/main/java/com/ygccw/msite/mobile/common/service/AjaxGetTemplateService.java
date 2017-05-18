package com.ygccw.msite.mobile.common.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.ygccw.msite.mobile.common.model.HtmlTemplate;
import core.framework.web.site.SiteSettings;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

/**
 * @author soldier
 */
@Service
public class AjaxGetTemplateService {
    @Inject
    private SiteSettings siteSettings;

    public HtmlTemplate getHtmlTemplate(String path) {
        String html;
        URL url = Resources.getResource(path);
        try {
            html = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HtmlTemplate htmlTemplate = new HtmlTemplate();
        htmlTemplate.setHtmlTemplate(html);
        htmlTemplate.setBaseUrl(siteSettings.baseUrl());
        htmlTemplate.setImageUrl(siteSettings.getImgServerUrl());
        return htmlTemplate;

    }

}
