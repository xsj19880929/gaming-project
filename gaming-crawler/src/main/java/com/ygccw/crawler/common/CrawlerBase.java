package com.ygccw.crawler.common;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.wltea.expression.datameta.Variable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
public class CrawlerBase {
    private final Logger logger = LoggerFactory.getLogger(CrawlerBase.class);
    @Inject
    private CHttpClient cHttpClient;


    public HashMap<String, List<HashMap<String, String>>> crawler(JSONObject task) throws Exception {
        return crawlerCommon(task, null);
    }

    public HashMap<String, List<HashMap<String, String>>> crawler(JSONObject task, BlockingQueue<JSONObject> nextTasks) throws Exception {
        return crawlerCommon(task, nextTasks);
    }

    private HashMap<String, List<HashMap<String, String>>> crawlerCommon(JSONObject task, BlockingQueue<JSONObject> nextTasks) throws Exception {
        HashMap<String, List<HashMap<String, String>>> results = new HashMap<String, List<HashMap<String, String>>>();
        String pageType = task.getString(Constants.TPLNAME);
        List<Variable> variables = new ArrayList<Variable>();
        variables.add(Variable.createVariable("task", task.toString()));
        if (task.get(Constants.URL) != null) {
            HttpUriRequest request = RequestBuilder.get().setUri(task.getString(Constants.URL))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0").build();
            HttpResponse httpResponse = cHttpClient.execute(request);
            String charset = "UTF-8";

            Header[] headers = httpResponse.getHeaders("Content-Type");
            for (Header header : headers) {
                if ("Content-Type".equals(header.getName()) && header.getValue().indexOf("charset=") != -1) {
                    charset = header.getValue().substring(header.getValue().indexOf("charset=") + 8);
                }
            }
            if (task.get("htmlCharset") != null && StringUtils.hasText(task.getString("htmlCharset"))) {
                charset = task.getString("htmlCharset");
            }
            String html = EntityUtils.toString(httpResponse.getEntity(), charset);
            // 简易公式赋值变量对象集合
            variables.add(Variable.createVariable("html", html));
        }
        // 解析工具
        Semantic semantic = new Semantic();
        results = semantic.semanticPage(pageType, variables, nextTasks);

        return results;
    }


}
