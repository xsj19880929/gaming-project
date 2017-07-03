package com.ygccw.wechat.utils;

import com.ygccw.wechat.commiturlsearch.model.SoSiteMapUrl;
import com.ygccw.wechat.commiturlsearch.model.UrlSet;
import core.framework.util.XMLBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
public class TestXml {
    public static void main(String[] args) {
        TestXml testXml = new TestXml();
        testXml.outXml();
    }

    public void outXml() {
        List<SoSiteMapUrl> list = new ArrayList<>();
        SoSiteMapUrl soSiteMapUrl = new SoSiteMapUrl();
        soSiteMapUrl.setChangefreq("daily");
        soSiteMapUrl.setLastmod("2017-02-05");
        soSiteMapUrl.setLoc("http://m.domain.com/abc.xhtml");
        soSiteMapUrl.setPriority("0.5");

        SoSiteMapUrl soSiteMapUrl2 = new SoSiteMapUrl();
        soSiteMapUrl2.setChangefreq("daily");
        soSiteMapUrl2.setLastmod("2017-02-05");
        soSiteMapUrl2.setLoc("http://m.domain.com/abc.xhtml");
        soSiteMapUrl2.setPriority("0.5");
        list.add(soSiteMapUrl);
        list.add(soSiteMapUrl2);

        UrlSet soSiteMap = new UrlSet();
        soSiteMap.setUrl(list);

        System.out.println(XMLBinder.toXML(soSiteMap));
    }
}
