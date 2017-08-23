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
//        String str = "{\"2017-08-12\":{\"baidu\":{\"count\":42},\"other\":{\"count\":13938},\"google\":{\"count\":3956},\"bing\":{\"count\":3436},\"sogou\":{\"count\":3066},\"yahoo\":{\"count\":38},\"360\":{\"count\":596}},\"2017-08-13\":{\"baidu\":{\"count\":67},\"other\":{\"count\":18292},\"google\":{\"count\":4370},\"bing\":{\"count\":2649},\"sogou\":{\"count\":3426},\"yahoo\":{\"count\":25},\"360\":{\"count\":946}},\"2017-08-14\":{\"baidu\":{\"count\":138},\"google\":{\"count\":2991},\"other\":{\"count\":16699},\"bing\":{\"count\":3036},\"sogou\":{\"count\":3966},\"yahoo\":{\"count\":80},\"360\":{\"count\":779}},\"2017-08-15\":{\"baidu\":{\"count\":41},\"google\":{\"count\":5770},\"other\":{\"count\":25649},\"bing\":{\"count\":2240},\"sogou\":{\"count\":3055},\"yahoo\":{\"count\":13},\"360\":{\"count\":2253}},\"2017-08-16\":{\"baidu\":{\"count\":44},\"other\":{\"count\":25768},\"google\":{\"count\":15639},\"bing\":{\"count\":2060},\"sogou\":{\"count\":4275},\"yahoo\":{\"count\":11},\"360\":{\"count\":1213}},\"2017-08-17\":{\"baidu\":{\"count\":47},\"other\":{\"count\":23198},\"google\":{\"count\":22219},\"bing\":{\"count\":1905},\"sogou\":{\"count\":3747},\"yahoo\":{\"count\":8},\"360\":{\"count\":1471}},\"2017-08-18\":{\"baidu\":{\"count\":48},\"other\":{\"count\":21428},\"google\":{\"count\":20594},\"bing\":{\"count\":2850},\"sogou\":{\"count\":12721},\"yahoo\":{\"count\":11},\"360\":{\"count\":1210}},\"2017-08-19\":{\"baidu\":{\"count\":54},\"other\":{\"count\":17489},\"google\":{\"count\":13847},\"bing\":{\"count\":3172},\"sogou\":{\"count\":14231},\"yahoo\":{\"count\":217},\"360\":{\"count\":1888}},\"2017-08-20\":{\"baidu\":{\"count\":54},\"other\":{\"count\":18740},\"google\":{\"count\":14372},\"bing\":{\"count\":2194},\"sogou\":{\"count\":3953},\"360\":{\"count\":995},\"yahoo\":{\"count\":157}},\"2017-08-21\":{\"baidu\":{\"count\":191},\"other\":{\"count\":24135},\"google\":{\"count\":50352},\"bing\":{\"count\":3062},\"sogou\":{\"count\":3629},\"yahoo\":{\"count\":51},\"360\":{\"count\":1056}},\"2017-08-22\":{\"baidu\":{\"count\":129},\"other\":{\"count\":27715},\"google\":{\"count\":30802},\"bing\":{\"count\":2123},\"sogou\":{\"count\":4175},\"yahoo\":{\"count\":23},\"360\":{\"count\":1327}}}";
//        Map<String, Object> data = JSONBinder.fromJSON(Map.class, str);
//        logAnalysisService.send(logAnalysisService.switchMailContent(data));
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
        send(switchMailContent(data));
    }

    /**
     * 邮件内容格式转换
     *
     * @param data
     * @return
     */

    public String switchMailContent(Map<String, Object> data) {
        StringBuilder stringBuilder = new StringBuilder("<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:800px;text-align:center;font:12px\">");
        int row = 0;
        for (Map.Entry entry : data.entrySet()) {
            row++;
            Map<String, Object> dataMap = (Map) entry.getValue();
            if (row == 1) {//表头
                stringBuilder.append("<tr>").append("<td>日期</td>");
                for (Map.Entry dataEntry : dataMap.entrySet()) {
                    String whichSpider = dataEntry.getKey().toString();
                    stringBuilder.append("<td>").append(whichSpider).append("</td>");
                }
                stringBuilder.append("</tr>");
            }
            stringBuilder.append("<tr>");
            String date = entry.getKey().toString();
            stringBuilder.append("<td>").append(date).append("</td>");
            for (Map.Entry dataEntry : dataMap.entrySet()) {
                Map<String, Object> countMap = (Map) dataEntry.getValue();
                String count = countMap.get("count").toString();
                stringBuilder.append("<td>").append(count).append("</td>");

            }
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
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
//        mail.addTo("290976515@qq.com");
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
        } else if (content.contains("YisouSpider")) {
            return "sm";
        }
        return "other";
    }
}
