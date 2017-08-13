package com.ygccw.crawler.schedule.service;

import com.ygccw.crawler.utils.SFTPUtil;
import core.framework.mail.Mail;
import core.framework.mail.MailSender;
import core.framework.util.DateUtils;
import core.framework.util.JSONBinder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soldier
 */
@Service
public class LogAnalysisService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public Map<String, Object> data = new LinkedHashMap<String, Object>();
    private String logDir = "D://logs//";
    private String remote = "/var/log/nginx";

    public static void main(String[] args) {

//        startWork();
//        LogAnalysisService logAnalysisService = new LogAnalysisService();
//        logAnalysisService.AnalysisWork();
//        logAnalysisService.readLog("D:\\logs\\www_access_2017-08-05.log");
    }

    public void AnalysisWork() {
        String dateStr = DateFormatUtils.format(DateUtils.add(new Date(), Calendar.DATE, -1), "yyyy-MM-dd");
        String fileName = "www_access_" + dateStr + ".log";
        File file = new File(logDir);
        if (!file.exists()) {
            logger.info(logDir + "不存在就创建");
            file.mkdirs();
        }
        SFTPUtil ftp = new SFTPUtil();
        ftp.connect();
        ftp.download(remote, fileName, logDir + fileName);
        ftp.disconnect();
        Map<String, Object> data = readLog(logDir + fileName);
        send(JSONBinder.toJSON(data));
    }

    public void send(String text) {
        MailSender sender = new MailSender();
        sender.setPort(25);
        sender.setHost("smtp.163.com");
        sender.setUsername("xsj19880929@163.com");
        sender.setPassword("xsj19880929hp");
        Mail mail = new Mail();
        mail.subject(DateFormatUtils.format(DateUtils.add(new Date(), Calendar.DATE, -1), "yyyy-MM-dd") + " 搜索引擎爬虫访问监控");
        mail.from("xsj19880929@163.com");
        mail.addTo("253855983@qq.com");
        mail.addTo("290976515@qq.com");
        mail.htmlBody(text);
        sender.send(mail);
        logger.info("send mail --------------------------");

    }

    /**
     * 读取log或者txt信息
     *
     * @param filePath
     * @return
     */
    public Map<String, Object> readLog(String filePath) {
        try {
            FileInputStream is = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if (line.equals(""))
                        continue;
                    else
                        analysisLog(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("读取一行数据时出错");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件读取路径错误FileNotFoundException");
        }
        System.out.println(JSONBinder.toJSON(data));
        return data;
    }

    private void analysisLog(String content) {
        //19/Jul/2017:05:30:12 +0800
        String dateStr = regexp(content, "\\[(.*)\\]");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyy:HH:mm:ss Z", Locale.ENGLISH);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String whichDay = simpleDate.format(date);
        if (data.get(whichDay) != null) {
            Map<String, Object> spider = (Map) data.get(whichDay);
            if (spider.get(whichSpider(content)) != null) {
                Map<String, Object> countMap = (Map) spider.get(whichSpider(content));
                if (countMap.get("count") != null) {
                    int count = (Integer) countMap.get("count");
                    countMap.put("count", count + 1);
                } else {
                    countMap.put("count", 1);
                }
            } else {
                Map<String, Object> countMap = new HashMap<>();
                countMap.put("count", 1);
                spider.put(whichSpider(content), countMap);
            }
        } else {
            Map<String, Object> spider = new HashMap<>();
            Map<String, Object> countMap = new HashMap<>();
            countMap.put("count", 1);
            spider.put(whichSpider(content), countMap);
            data.put(whichDay, spider);
        }


    }

    // 正则解析
    private String regexp(Object value, String regexp) {
        Map<String, Pattern> patterns = new WeakHashMap<String, Pattern>();
        if (regexp == null || regexp.toString().isEmpty()) {
            return "";
        }
        Pattern p = patterns.get(regexp);
        if (p == null) {
            p = Pattern.compile(regexp);
            patterns.put(regexp, p);
        }
        Matcher m = p.matcher(value.toString().replace("\n", ""));
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

    private String whichSpider(String content) {
        if (content.contains("Googlebot")) {
            return "google";
        } else if (content.contains("Baiduspider")) {
            return "baidu";
        } else if (content.contains("yahoo")) {
            return "yahoo";
        } else if (content.contains("bingbot")) {
            return "bing";
        } else if (content.contains("Sogou web spider")) {
            return "sogou";
        } else if (content.contains("360Spider")) {
            return "360";
        }
        return "other";
    }
}
