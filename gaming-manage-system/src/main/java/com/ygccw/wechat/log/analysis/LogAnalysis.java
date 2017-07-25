package com.ygccw.wechat.log.analysis;

import core.framework.util.JSONBinder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soldier
 */
public class LogAnalysis {
    public Map<String, Object> data = new LinkedHashMap<String, Object>();

    public static void main(String[] args) {
        LogAnalysis logAnalysis = new LogAnalysis();
        logAnalysis.readLog("C:\\Users\\Administrator\\Desktop\\电竞\\www_access.log");
    }

    /**
     * 读取log或者txt信息
     *
     * @param filePath
     * @return
     */
    public List<String> readLog(String filePath) {
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
        return null;
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
