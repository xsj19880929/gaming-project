package com.ygccw.wechat.commiturlsearch.service;

import com.ygccw.wechat.commiturlsearch.model.SoSiteMapUrl;
import com.ygccw.wechat.commiturlsearch.model.UrlSet;
import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.enums.WebType;
import com.ygccw.wechat.common.commiturlsearch.service.CommitUrlSearchService;
import core.framework.util.XMLBinder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class CommitUrlSearchModelService {
    @Inject
    private CommitUrlSearchService commitUrlSearchService;

    public void create360SiteMap() {
        CommitUrlSearch commitUrlSearch = new CommitUrlSearch();
        commitUrlSearch.setWebType(WebType.baidu);
        int offset = 0;
        int fetchSize = 10000;
        List<CommitUrlSearch> commitUrlSearchList = null;
        do {
            List<SoSiteMapUrl> list = new ArrayList<>();
            commitUrlSearchList = commitUrlSearchService.list(commitUrlSearch, offset, fetchSize);
            for (CommitUrlSearch commitUrlSearch1 : commitUrlSearchList) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SoSiteMapUrl soSiteMapUrl = new SoSiteMapUrl();
                soSiteMapUrl.setChangefreq("daily");
                soSiteMapUrl.setLastmod(simpleDateFormat.format(commitUrlSearch1.getCreateTime()));
                soSiteMapUrl.setLoc(commitUrlSearch1.getUrl().replace("http://www.55djw.com", "http://m.55djw.com"));
                soSiteMapUrl.setPriority("0.5");
                list.add(soSiteMapUrl);
            }
            UrlSet soSiteMap = new UrlSet();
            soSiteMap.setUrl(list);
            System.out.println();
            try {
                String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XMLBinder.toXML(soSiteMap);
                File file = new File("D:\\360sitemap" + offset + ".xml");
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }
            offset = offset + fetchSize;
        } while (!commitUrlSearchList.isEmpty());
    }


}
