package com.ygccw.crawler.schedule.service;

import com.ygccw.crawler.common.CHttpClient;
import com.ygccw.crawler.utils.DateUtils;
import core.framework.mail.Mail;
import core.framework.mail.MailSender;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author soldier
 */
@Service
public class CheckWebSiteService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    private CHttpClient cHttpClient;

    public void startTread() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                startWork("http://www.55djw.com");
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                startWork("http://m.55djw.com");
            }
        });
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    private void startWork(String url) {
        int webBreakTimes = 0;
        while (checkWeb(url) != 200) {
            webBreakTimes++;
            try {
                Thread.sleep(20000);//等待20秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (webBreakTimes == 5) {//已经挂掉一分钟那就要邮件通知
                send(url + "已经挂了一分钟了，时间：" + DateUtils.formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                logger.info(url + "已经挂了一分钟了");
                break;
            }
        }
    }

    private int checkWeb(String url) {
        int statusCode = 0;
        try {
            HttpUriRequest request = RequestBuilder.get().setUri(url).build();
            HttpResponse httpResponse = cHttpClient.execute(request);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            request.abort();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return statusCode;
    }

    private void send(String text) {
        MailSender sender = new MailSender();
        sender.setPort(25);
        sender.setHost("smtp.163.com");
        sender.setUsername("xsj19880929@163.com");
        sender.setPassword("xsj19880929hp");
        Mail mail = new Mail();
        mail.subject("监控网站是否正常");
        mail.from("xsj19880929@163.com");
        mail.addTo("253855983@qq.com");
        mail.addTo("290976515@qq.com");
        mail.htmlBody(text);
        sender.send(mail);
        logger.info("send mail --------------------------");

    }
}
