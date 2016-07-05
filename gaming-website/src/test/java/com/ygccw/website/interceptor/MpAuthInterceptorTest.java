package com.ygccw.website.interceptor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lucian.lin on 16/5/6.
 */
public class MpAuthInterceptorTest {

    @Test
    public void testGetMpId() throws Exception {

        String uri = "http://123.dev.yaxia.com/?";
        int startIndex = uri.indexOf("http://") + "http://".length();
        int stopIndex = uri.indexOf('.', startIndex);

        String strMpId = uri.substring(startIndex, stopIndex);
        Long mpId = Long.valueOf(strMpId);
        Assert.assertNotNull(mpId);
        Assert.assertEquals("mpId", 123L, mpId.longValue());

    }

    @Test
    public void testGetBaseUrl() throws Exception {
        StringBuilder url = new StringBuilder("http://123.dev.yaxia.com");
        int stopIndex = url.indexOf("/", "http://".length());
        if (stopIndex < 0)
            stopIndex = url.length();
        String baseUrl = url.substring(0, stopIndex);
        Assert.assertEquals("baseUrl", "http://123.dev.yaxia.com", baseUrl);

        StringBuilder url2 = new StringBuilder("http://123.dev.yaxia.com/");
        stopIndex = url2.indexOf("/", "http://".length());
        if (stopIndex < 0)
            stopIndex = url.length();
        baseUrl = url.substring(0, stopIndex);

        Assert.assertEquals("baseUrl", "http://123.dev.yaxia.com", baseUrl);
    }
}