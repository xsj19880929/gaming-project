package com.ygccw.crawler.common;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.wltea.expression.datameta.Variable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CrawlerBase {
    private final Logger logger = LoggerFactory.getLogger(CrawlerBase.class);
    @Inject
    private CHttpClient cHttpClient;

    public HashMap<String, List<HashMap<String, String>>> crawler(JSONObject task) throws Exception {
        return crawlerCommon(task, null);
    }

    public HashMap<String, List<HashMap<String, String>>> crawler(JSONObject task, List<JSONObject> nextTasks) throws Exception {
        return crawlerCommon(task, nextTasks);
    }

    private HashMap<String, List<HashMap<String, String>>> crawlerCommon(JSONObject task, List<JSONObject> nextTasks) throws Exception {
        HashMap<String, List<HashMap<String, String>>> results = new HashMap<String, List<HashMap<String, String>>>();
        String pageType = task.getString(Constants.TPLNAME);
        HttpUriRequest request = RequestBuilder.get().setUri(task.getString(Constants.URL)).build();
        HttpResponse httpResponse = cHttpClient.execute(request);
        String html = EntityUtils.toString(httpResponse.getEntity());
        // 简易公式赋值变量对象集合
        List<Variable> variables = new ArrayList<Variable>();
        variables.add(Variable.createVariable("task", task.toString()));
        variables.add(Variable.createVariable("html", html));
        // 解析工具
        Semantic semantic = new Semantic();
        results = semantic.semanticPage(pageType, variables, nextTasks);

        return results;
    }


}
