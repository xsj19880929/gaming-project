package com.ygccw.crawler.schedule.service;

import com.ygccw.crawler.common.CHttpClient;
import com.ygccw.crawler.utils.ConLinux;
import com.ygccw.crawler.utils.DateUtils;
import com.ygccw.wechat.common.checkwebsite.entity.CheckWebSite;
import com.ygccw.wechat.common.checkwebsite.sevice.CheckWebSiteServiceI;
import core.framework.mail.Mail;
import core.framework.mail.MailSender;
import core.framework.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author soldier
 */
@Service
public class CheckWebSiteService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String mUrl = "http://m.55djw.com";
    private final String pcUrl = "http://www.55djw.com";
    @Inject
    private CHttpClient cHttpClient;
    @Inject
    private CheckWebSiteServiceI checkWebSiteServiceI;

    public void startTread() {
        List<CheckWebSite> checkWebSiteList = checkWebSiteServiceI.list(null, 0, 100);
        if (checkWebSiteList != null && !checkWebSiteList.isEmpty()) {
            int size = checkWebSiteList.size();
            ExecutorService executor = Executors.newFixedThreadPool(size > 20 ? 20 : size);
            for (final CheckWebSite checkWebSite : checkWebSiteList) {
                if (checkWebSite.getType() == 1) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            startWork(checkWebSite.getUrl(), checkWebSite.getEmail(), checkWebSite.getRestart());
                        }
                    });
                } else if (checkWebSite.getType() == 2) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            startWorkFileSystem(checkWebSite.getUrl(), checkWebSite.getEmail());
                        }
                    });
                }
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
        }
    }

    private void startWork(String url, String email, Integer restart) {
        int webBreakTimes = 0;
        while (checkWeb(url) != 200) {
            webBreakTimes++;
            try {
                Thread.sleep(20000);//等待20秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (webBreakTimes == 5) {//已经挂掉一分钟那就要邮件通知
                send("监控网站是否正常", url + "已经挂了一分钟了，时间：" + DateUtils.formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"), email);
                logger.info(url + "已经挂了一分钟了");
                if (StringUtils.equals(url, pcUrl) && restart == 1) {
                    ConLinux conLinux = new ConLinux();
                    conLinux.restartPcSite();
                    send("自动重启服务", url + "已经重启完毕，时间：" + DateUtils.formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"), email);
                    logger.info(url + "自动重启");
                } else if (StringUtils.equals(url, mUrl) && restart == 1) {
                    ConLinux conLinux = new ConLinux();
                    conLinux.restartMSite();
                    send("自动重启服务", url + "已经重启完毕，时间：" + DateUtils.formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"), email);
                    logger.info(url + "自动重启");
                }
                break;
            }
        }
    }

    private void startWorkFileSystem(String url, String email) {
        int webBreakTimes = 0;
        while (!checkWebIsWork(url)) {
            webBreakTimes++;
            try {
                Thread.sleep(20000);//等待20秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (webBreakTimes == 5) {//已经挂掉一分钟那就要邮件通知
                send("监控网站是否正常", url + "已经挂了一分钟了，时间：" + DateUtils.formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"), email);
                logger.info(url + "已经挂了一分钟了");
                ConLinux conLinux = new ConLinux();
                conLinux.restartFileSystem();
                send("自动重启服务", url + "已经重启完毕，时间：" + DateUtils.formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"), email);
                logger.info(url + "自动重启");
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

    private Boolean checkWebIsWork(String url) {
        int statusCode = 0;
        String html = "";
        try {
            HttpUriRequest request = RequestBuilder.get().setUri(url).build();
            HttpResponse httpResponse = cHttpClient.execute(request);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            html = EntityUtils.toString(httpResponse.getEntity());
            request.abort();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (statusCode == 200 && !"".equals(html)) {
            return true;
        }
        return false;
    }

    private void send(String title, String text, String email) {
        MailSender sender = new MailSender();
        sender.setPort(25);
        sender.setHost("smtp.163.com");
        sender.setUsername("xsj19880929@163.com");
        sender.setPassword("xsj19880929hp");
        Mail mail = new Mail();
        mail.subject(title);
        mail.from("xsj19880929@163.com");
        String[] emailList = email.split(";");
        for (String e : emailList) {
            mail.addTo(e);
        }
        mail.htmlBody(text);
        sender.send(mail);
        logger.info("send mail --------------------------");

    }
}
