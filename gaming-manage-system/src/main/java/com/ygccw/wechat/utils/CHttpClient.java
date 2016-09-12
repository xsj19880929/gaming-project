package com.ygccw.wechat.utils;

import com.google.common.base.Charsets;
import core.framework.util.JSONBinder;
import core.framework.util.TimeLength;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by guoshuai on 16/8/4.
 */
@Service
public class CHttpClient {
    private static final TimeLength TIMEOUT = TimeLength.seconds(30);
    private static HttpClient httpClient;

    static {
        try {
            HttpClientBuilder builder = HttpClients.custom();
            builder.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .setSslcontext(new SSLContextBuilder()
                            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                            .build());
            int cpuNum = Runtime.getRuntime().availableProcessors();
            builder.setMaxConnPerRoute(cpuNum).setMaxConnTotal(cpuNum * 10);
            builder.setDefaultSocketConfig(SocketConfig.custom()
                    .setSoKeepAlive(true).build());
            builder.setDefaultRequestConfig(RequestConfig.custom()
                    .setSocketTimeout((int) TIMEOUT.toMilliseconds())
                    .setConnectTimeout((int) TIMEOUT.toMilliseconds()).build());
            httpClient = builder.build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new IllegalStateException(e);
        }
    }

    @PreDestroy
    public static void close() throws IOException {
        ((CloseableHttpClient) httpClient).close();
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return execute(request, (HttpClientContext) null);
    }

    public HttpResponse execute(HttpHost target, HttpUriRequest request) throws IOException {
        return httpClient.execute(target, request);
    }

    public HttpResponse execute(HttpUriRequest request, HttpClientContext clientContext) throws IOException {
        return httpClient.execute(request, clientContext);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpClientContext clientContext) throws IOException {
        return httpClient.execute(request, responseHandler, clientContext);
    }

    public <T> T execute(HttpHost target, HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpClientContext clientContext) throws IOException {
        return httpClient.execute(target, request, responseHandler, clientContext);
    }

    public <T> T execute(HttpHost target, HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        return httpClient.execute(target, request, responseHandler);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        return execute(request, responseHandler, null);
    }

    public <T> T execute(HttpUriRequest request, final Class<T> clazz) throws IOException {
        return execute(request, new AbstractResponseHandler<T>() {
            @Override
            public T handleEntity(HttpEntity entity) throws IOException {
                String s = EntityUtils.toString(entity, Charsets.UTF_8);

                return JSONBinder.fromJSON(clazz, s);
            }
        });
    }

    public <T> List<T> executeForList(HttpUriRequest request, final Class<T> clazz) throws IOException {
        return execute(request, new AbstractResponseHandler<List<T>>() {
            @Override
            public List<T> handleEntity(HttpEntity entity) throws IOException {
                String s = EntityUtils.toString(entity, Charsets.UTF_8);

                return JSONBinder.fromJSONList(clazz, s);
            }
        });
    }

    public <T> T execute(HttpUriRequest request, HttpClientContext clientContext, final Class<T> clazz) throws IOException {
        return execute(request, new AbstractResponseHandler<T>() {
            @Override
            public T handleEntity(HttpEntity entity) throws IOException {
                String s = EntityUtils.toString(entity, Charsets.UTF_8);

                return JSONBinder.fromJSON(clazz, s);
            }
        }, clientContext);
    }

    public <T> List<T> executeForList(HttpUriRequest request, HttpClientContext clientContext, final Class<T> clazz) throws IOException {
        return execute(request, new AbstractResponseHandler<List<T>>() {
            @Override
            public List<T> handleEntity(HttpEntity entity) throws IOException {
                String s = EntityUtils.toString(entity, Charsets.UTF_8);

                return JSONBinder.fromJSONList(clazz, s);
            }
        }, clientContext);
    }

    public String executeString(HttpUriRequest request) throws IOException {
        return execute(request, new BasicResponseHandler());
    }

    public String executeString(HttpUriRequest request, HttpClientContext httpClientContext) throws IOException {
        return execute(request, new BasicResponseHandler(), httpClientContext);
    }

    /*public static void main(String[] args) throws IOException {
        *//*HttpUriRequest request = RequestBuilder.get("http://api.jisuapi.com/proxy/get")
            .addParameter("num", "200")
            .addParameter("type", "1")
           // .addParameter("area", "福建,上海,广东,浙江,江苏")
            .addParameter("appkey", "52f68d2897f96b35").build();
        HttpResponse response = httpClient.execute(request);
        Preconditions.checkState(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        System.out.println(EntityUtils.toString(response.getEntity()));*//*
        //RequestConfig config = RequestConfig.custom().setProxy(new HttpHost("124.240.187.78", 80, "http")).build();
       // HttpHost target = new HttpHost("market.tea.suryani.cn", 80, "http");
        HttpUriRequest request = RequestBuilder.get("http://member.pingan.com.cn/pinganone/pa/paic/common/appvcode.do").build();
        HttpResponse execute = httpClient.execute(request);
        System.out.println(EntityUtils.toString(execute.getEntity()));

    }*/
}
