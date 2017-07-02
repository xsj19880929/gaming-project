package com.ygccw.wechat.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author soldier
 */
@XmlRootElement(name = "urlset")
@XmlAccessorType(XmlAccessType.FIELD)
public class UrlSet {
    private List<SoSiteMapUrl> url;

    public List<SoSiteMapUrl> getUrl() {
        return url;
    }

    public void setUrl(List<SoSiteMapUrl> url) {
        this.url = url;
    }
}
