package com.ygccw.wechat.scheduler.service;

import com.ygccw.wechat.common.commiturlsearch.entity.CommitUrlSearch;
import com.ygccw.wechat.common.commiturlsearch.enums.WebType;
import com.ygccw.wechat.common.commiturlsearch.service.CommitUrlSearchService;
import core.framework.util.JSONBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

/**
 * @author soldier
 */
@Service
public class BaiDuPostService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private CommitUrlSearchService commitUrlSearchService;

    public void baiDuPost() {
        String postUrl = "http://data.zz.baidu.com/urls?site=www.55djw.com&token=6TWYzCvQtyhqyxZF";
        int offset = 0;
        int fetchSize = 2000;
        CommitUrlSearch commitUrlSearch = new CommitUrlSearch();
        commitUrlSearch.setIfCommit(0);
        commitUrlSearch.setWebType(WebType.baidu);
        List<CommitUrlSearch> commitUrlSearchList = null;
        do {
            commitUrlSearchList = commitUrlSearchService.list(commitUrlSearch, offset, fetchSize);
            if (commitUrlSearchList != null && !commitUrlSearchList.isEmpty()) {
                HashMap mapResult = post(postUrl, commitUrlSearchList);
                try {
                    if ((Integer) mapResult.get("success") == commitUrlSearchList.size()) {
                        for (CommitUrlSearch commitUrlSearch1 : commitUrlSearchList) {
                            commitUrlSearch1.setIfCommit(1);
                            commitUrlSearchService.update(commitUrlSearch1);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            offset = offset + fetchSize;

        } while (commitUrlSearchList != null && !commitUrlSearchList.isEmpty());

    }

    /**
     * @param postUrl
     * @param commitUrlSearchList 不能超过2000条
     * @return
     */
    private HashMap post(String postUrl, List<CommitUrlSearch> commitUrlSearchList) {
        if (null == postUrl || null == commitUrlSearchList || commitUrlSearchList.size() == 0) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(postUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", "83");
            conn.setRequestProperty("Content-Type", "text/plain");

            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            StringBuffer param = new StringBuffer();
            for (CommitUrlSearch commitUrlSearch : commitUrlSearchList) {
                param.append(commitUrlSearch.getUrl()).append("\n");
            }
            out.print(param.toString().trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (RuntimeException e) {
            logger.error("发送post请求出现异常！" + e);
        } catch (Exception e) {
            logger.error("发送post请求出现异常！" + e);
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        logger.info("结果：" + result);
        return JSONBinder.fromJSON(HashMap.class, result.toString());
    }
}
