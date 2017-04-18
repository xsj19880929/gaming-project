package com.ygccw.crawler.common;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IKFunction {
    public static Map<String, Pattern> patterns = new WeakHashMap<String, Pattern>();
    private static XPath xpath = null;
    private static Map<String, XPathExpression> xPathExps = new WeakHashMap<String, XPathExpression>();

    static {
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
    }

    private final Logger logger = LoggerFactory.getLogger(IKFunction.class);
    private final String imageServer = "http://image.55djw.com/image/url/upload?url=";
    private final String downServer = "http://image.55djw.com";

    // 格式化为数组对象
    public static Object arrayFmt(Object obj) {
        try {
            return JSONArray.fromObject(obj).toArray();
        } catch (Exception e) {
            return "";
        }
    }

    // 格式化url
    public static String urlFmt(Object value) {
        try {
            return URLEncoder.encode(value.toString(), "GBK");
        } catch (Exception e) {
            return "";
        }
    }

    // 获取内容
    private static String getNodeText(Node node, boolean b) {
        if ("#comment".equals(node.getLocalName()) && b) {
            return "";
        }
        String value = node.getNodeValue();
        NodeList nodes = node.getChildNodes();
        if (nodes != null) {
            for (int i = 0; i < nodes.getLength(); i++) {
                value += getNodeText(nodes.item(i), b);
            }
        }
        return value;
    }

    // 正则解析
    public static String regexp(Object value, String regexp) {
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

    // 正则解析
    public static int rowRegexp(Object value, String regexp) {
        if (regexp == null || regexp.toString().isEmpty()) {
            return 0;
        }
        Pattern p = patterns.get(regexp);
        if (p == null) {
            p = Pattern.compile(regexp);
            patterns.put(regexp, p);
        }
        Matcher m = p.matcher(value.toString().replace("\n", ""));
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list.size();
    }

    // 正则解析
    public static String regexpRows(Object value, String regexp, int row) {
        if (regexp == null || regexp.toString().isEmpty()) {
            return "";
        }
        Pattern p = patterns.get(regexp);
        if (p == null) {
            p = Pattern.compile(regexp);
            patterns.put(regexp, p);
        }
        Matcher m = p.matcher(value.toString().replace("\n", ""));
        List<String> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.group(1));
        }
        if (list.isEmpty()) {
            return "";
        } else {
            return list.get(row - 1);
        }

    }

    // 字符串截取
    public static String subStr(Object obj, int start, int end) {
        if (obj == null) {
            return "";
        }
        String str = obj.toString();
        if (start < 0 && end <= 0 && start <= end && str.length() > -start) {
            return str.substring(str.length() + start, str.length() + end);
        }
        if (start >= 0 && end >= 0 && start <= end && str.length() > end) {
            return str.substring(start, end);
        }
        return str;
    }

    // 向上取整
    public static int divided(Object val1, Object val2) {
        int page = 0;
        try {
            page = (int) Math.ceil(Double.parseDouble(val1.toString()) / Double.parseDouble(val2.toString()));
        } catch (Exception e) {
        }
        return page;
    }

    // 系统时间字符串 yyyy-MM-dd HH:mm:ss
    public static String sysDateStr() {
        return new SimpleDateFormat(Constants.JAVA_DATE_FORMAT).format(new Date());
    }

    // 系统时间年月日 yyyyMMdd
    public static String sysDateYmd() {
        return new SimpleDateFormat(Constants.DATE_FORMAT_YMD).format(new Date());
    }

    // 计算时间差
    public static long subTime(Object oneDate, Object twoDate, String dateFormat) {
        Long subTime = 0L;
        try {
            Long oneDateLong = new SimpleDateFormat(dateFormat).parse(oneDate.toString()).getTime();
            Long twoDateLong = new SimpleDateFormat(dateFormat).parse(twoDate.toString()).getTime();
            subTime = oneDateLong - twoDateLong;
        } catch (ParseException e) {
            return 0;
        }
        return subTime;
    }

    // 数字格式化
    public static String numFormat(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace(",", "").replace("￥", "").trim();
    }

    // 字符串包含
    public static boolean myContains(Object value, String str) {
        try {
            return value.toString().contains(str);
        } catch (Exception e) {
            return false;
        }
    }

    public static String objSubStr(Object obj, String val) {
        try {
            String str = obj.toString();
            String[] valarray = val.split("-");
            for (String ext : valarray) {
                str = str.replace(ext, "");
            }
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    // uuid生成器
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // 退还字符串
    public static String replaceStr(Object obj, String oldChar, String newChar) {
        try {
            String str = obj.toString();
            str = str.replace(oldChar, newChar);
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    // 退还字符串
    public static String replaceAllStr(Object obj, String oldChar, String newChar) {
        try {
            String str = obj.toString();
            str = str.replaceAll(oldChar, newChar);
            return str;
        } catch (Exception e) {
            return "";
        }
    }

    // 字符串类型转double类型
    public static double string2Double(String value) {
        if (value == null || "".equals(value)) {
            return 0;
        }
        return Double.parseDouble(value);
    }

    // 计算字符串MD5值
    public static String md5(Object object) {
        try {
            String content = (String) object;
            byte[] src = content.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(src);

            return byte2hex(md5.digest()).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String temp = "";
        for (int n = 0; n < b.length; n++) {
            temp = Integer.toHexString(b[n] & 0XFF);
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
        }

        return hs.toUpperCase();
    }

    public static void main(String[] args) {
//        String a = "￥1,000.00";
//        System.out.println(regexp(a, "(\\d{1,3})"));
//        String b = "http://car.autohome.com.cn/price/brand-34.html";
//        System.out.println(regexp(b, "(\\d{1,5})"));
//        String c = " 19.98万 <a href=\"http://www.autohome.com.cn/buycar.html?specid=17050\"><i class=\"icon16 icon16-calendar\"></i></a>";
//        System.out.println(regexp(c, "(\\d*[.]\\d*[万])"));
//        String d = " var keyLink = {\"r\"};var config = {\"returncode\":0,\"message\":\"成功\"};";
//        System.out.println(regexp(regexp(d, "(var config = [\\s\\S]*;)"), "([{][\\s\\S]*[}])"));
//        String e = "车身外观(共有321张图片)";
//        System.out.println(regexp(e, "([\\s\\S]{4})"));
//        String f = b;
//        System.out.println(md5(f));
//        System.out.println(md5(b));
//        String g = "9.02-10.3万";
//        String h = regexp(g, "(\\d*[.]\\d*)");
        // double i = Double.parseDouble(h) * 10000;
//        System.out.println(h);
//        String l = "【别克 君威】现货";
//        String i = regexp(l, "】(.*)");
//        System.out.println(i);
//        String g = "国产现车|幻影黑/棕|手续3天内|销全国";
//        IKFunction ikFunction = new IKFunction();
////        ikFunction.array(ikFunction.split(g, "|"), 2);
//        String h = regexp(ikFunction.array(ikFunction.split(g, "\\|"), 2), "(.*)");
//        System.out.println(h);

//        String html = "photos.push({ orig: 'https://upload.shunwang.com/2016/0627/1467032515821.jpg', big: 'https://upload.shunwang.com/2016/0627/1467032515821.jpg', thumb: 'https://upload.shunwang.com/2016/0627/thumb_100_75_1467032515821.jpg', note: \"0dadf3718991d7bb1218443c2be665c9\" });\n" +
//                "                    photos.push({ orig: 'https://upload.shunwang.com/2016/0627/1467032515275.jpg', big: 'https://upload.shunwang.com/2016/0627/1467032515275.jpg', thumb: 'https://upload.shunwang.com/2016/0627/thumb_100_75_1467032515275.jpg', note: \"23715edc549f6611a62f6b6116827fc3\" });";
//        System.out.println(regexpRows(html, "orig: '(.*?)', big", 1));
    }

    // 格式化为tidyDom对象（org.w3c.dom.Document）
    public Object tidyDomFmt(Object obj) {
        return MyParser.dealHtml(obj.toString());
    }

    // 格式化为json对象
    public Object jsonFmt(Object obj) {
        try {
            return JSONObject.fromObject(obj);
        } catch (Exception e) {
        }
        return new JSONObject();
    }

    // 格式化时间(yyyy-MM-dd HH:mm:ss)
    public String dateFmt(Object obj, String format) {
        if (obj == null || obj.toString().isEmpty()) {
            return "";
        }
        String value = obj.toString();
        value = value.toString().trim();
        format = format.trim();
        String[] formats = format.split("\\|");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String yesterday = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String byesterday = sdf.format(cal.getTime());
        String year = cal.get(Calendar.YEAR) + "";
        long sysTime = System.currentTimeMillis();
        if (value.indexOf("秒") != -1) {
            long time = sysTime - Integer.parseInt(regexp(value, "(\\d*)")) * 1000;
            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = new Date(time);
            return sdft.format(dt);

        }
        if (value.indexOf("分") != -1) {
            long time = sysTime - Integer.parseInt(regexp(value, "(\\d*)")) * 60000;
            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = new Date(time);
            return sdft.format(dt);
        }
        if (value.indexOf("半小时") != -1) {
            long time = sysTime - 60000 * 30;
            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = new Date(time);
            return sdft.format(dt);
        }
        if (value.indexOf("小时") != -1) {
            long time = sysTime - Integer.parseInt(regexp(value, "(\\d*)")) * 60000 * 60;
            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = new Date(time);
            return sdft.format(dt);
        }
        if (value.indexOf("天前") != -1) {
            long time = sysTime - Integer.parseInt(regexp(value, "(\\d*)")) * 60000 * 60 * 24;
            SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = new Date(time);
            return sdft.format(dt);
        }

        if (value.indexOf("今天") != -1) {
            return today + " " + regexp(value, "(\\d*:\\d*)") + ":00";
        }
        if (value.indexOf("昨天") != -1) {
            return yesterday + " " + regexp(value, "(\\d*:\\d*)") + ":00";
        }
        if (value.indexOf("前天") != -1) {
            return byesterday + " " + regexp(value, "(\\d*:\\d*)") + ":00";
        }

        for (String str : formats) {
            try {
                if (str.equals("EEE MMM dd HH:mm:ss Z yyyy")) {
                    SimpleDateFormat in = new SimpleDateFormat(str, Locale.US);
                    SimpleDateFormat out = new SimpleDateFormat(Constants.JAVA_DATE_FORMAT);
                    return out.format(in.parse(value));
                }
                SimpleDateFormat sdft = new SimpleDateFormat(str);
                Date d = sdft.parse(value);
                if (str.equals("MM月dd日HH:mm")) {
                    sdft.applyPattern(Constants.NOYEAR_DATE_FORMAT);
                    return year + "-" + sdft.format(d);
                } else if (str.equals("MM-dd")) {
                    sdft.applyPattern(str);
                    return year + "-" + sdft.format(d) + " 00:00:00";
                } else if (str.equals("MM-dd HH:mm")) {
                    sdft.applyPattern(str);
                    return year + "-" + sdft.format(d) + ":00";
                } else if (str.equals("HH:mm")) {
                    sdft.applyPattern(str);
                    return today + " " + sdft.format(d) + ":00";
                } else {
                    sdft.applyPattern(Constants.JAVA_DATE_FORMAT);
                    return sdft.format(d);
                }
            } catch (ParseException e) {
                continue;
            }
        }

        return "";
    }

    // 标准化清理
    public Object clean(Object obj, Object url) {
        try {
            return MyParser.prepare(url.toString(), obj.toString());
        } catch (Exception e) {
        }
        return "";
    }

    // xpath解析获取记录数
    public int rows(Object tidyDom, String xPath) {
        try {
            XPathExpression expr = xPathExps.get(xPath);
            if (expr == null) {
                expr = xpath.compile(xPath);
                xPathExps.put(xPath, expr);
            }
            NodeList pn = (NodeList) expr.evaluate(tidyDom, XPathConstants.NODESET);
            return pn != null ? pn.getLength() : 0;
        } catch (Exception e) {
        }
        return 0;
    }

    // 数组获取记录数
    public int rowsArray(Object obj) {
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).size();
        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) (obj);
            return array.length;
        } else {
            try {
                return JSONArray.fromObject(obj).size();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    // xpath解析获取所有匹配项（去除注释）
    public String xPath(Object tidyDom, String xPath) {
        String results = "";
        try {
            XPathExpression expr = xPathExps.get(xPath);
            if (expr == null) {
                expr = xpath.compile(xPath);
                xPathExps.put(xPath, expr);
            }
            NodeList pn = (NodeList) expr.evaluate(tidyDom, XPathConstants.NODESET);
            for (int j = 0; j < pn.getLength(); j++) {
                String s = getNodeText(pn.item(j), true).trim();
                if (results.length() > 0) {
                    results += "\t";
                }
                results += s;
            }
        } catch (Exception e) {
        }
        return results;
    }

    // xpath行解析获取第一个匹配项（去除注释）
    public String xPathRows(Object tidyDom, String xPath) {
        List<String> results = new ArrayList<String>();
        try {
            XPathExpression expr = xPathExps.get(xPath);
            if (expr == null) {
                expr = xpath.compile(xPath);
                xPathExps.put(xPath, expr);
            }
            NodeList pn = (NodeList) expr.evaluate(tidyDom, XPathConstants.NODESET);
            for (int j = 0; j < pn.getLength(); j++) {
                String s = getNodeText(pn.item(j), true).trim();
                results.add(s);
            }
        } catch (Exception e) {
        }
        return results.size() > 0 ? results.get(0) : "";
    }

    // xpath列解析获取第row个匹配项（去除注释）
    public String xPathCols(Object tidyDom, String xPath, int row) {
        List<String> results = new ArrayList<String>();
        try {
            XPathExpression expr = xPathExps.get(xPath);
            if (expr == null) {
                expr = xpath.compile(xPath);
                xPathExps.put(xPath, expr);
            }
            NodeList pn = (NodeList) expr.evaluate(tidyDom, XPathConstants.NODESET);
            for (int j = 0; j < pn.getLength(); j++) {
                String s = getNodeText(pn.item(j), true).trim();
                results.add(s);
            }
        } catch (Exception e) {
        }
        return results.size() > (row - 1) ? results.get(row - 1) : "";
    }

    // xpath行解析获取第一个匹配项（保留注释）
    public String xPathCommentRows(Object tidyDom, String xPath) {
        List<String> results = new ArrayList<String>();
        try {
            XPathExpression expr = xPathExps.get(xPath);
            if (expr == null) {
                expr = xpath.compile(xPath);
                xPathExps.put(xPath, expr);
            }
            NodeList pn = (NodeList) expr.evaluate(tidyDom, XPathConstants.NODESET);
            for (int j = 0; j < pn.getLength(); j++) {
                String s = getNodeText(pn.item(j), false).trim();
                results.add(s);
            }
        } catch (Exception e) {
        }
        return results.size() > 0 ? results.get(0) : "";
    }

    // xpath列解析获取第row个匹配项（保留注释）
    public String xPathCommentCols(Object tidyDom, String xPath, int row) {
        List<String> results = new ArrayList<String>();
        try {
            XPathExpression expr = xPathExps.get(xPath);
            if (expr == null) {
                expr = xpath.compile(xPath);
                xPathExps.put(xPath, expr);
            }
            NodeList pn = (NodeList) expr.evaluate(tidyDom, XPathConstants.NODESET);
            for (int j = 0; j < pn.getLength(); j++) {
                String s = getNodeText(pn.item(j), false).trim();
                results.add(s);
            }
        } catch (Exception e) {
        }
        return results.size() > (row - 1) ? results.get(row - 1) : "";
    }

    // jsoup行解析获取第一个匹配项
    public String jsoupRows(Object html, String jsoup) {
        if (html == null) {
            return "";
        }
        org.jsoup.nodes.Document soup = Jsoup.parse(html.toString());
        org.jsoup.select.Elements newHtml = soup.select(jsoup);
        if (newHtml != null) {
            return newHtml.html();
        } else {
            return "";
        }
    }

    // jsoup过滤标签解析
    public String jsoupHtml(Object html, String jsoup) {
        org.jsoup.nodes.Document soup = Jsoup.parse(html.toString());
        org.jsoup.select.Elements newHtml = soup.select(jsoup);
        if (newHtml != null) {
            Whitelist tags = new Whitelist();
            tags.addTags("div", "table", "tbody", "tr", "td", "p", "br", "ul", "li", "h1", "h2", "h3", "h4", "h5");
            tags.addAttributes("img", "src");
            return Jsoup.clean(newHtml.html(), tags);
        } else {
            return "";
        }
    }

    // jsoup过滤标签解析
    public String jsoupHtmlDownImage(Object html, String jsoup) {
        org.jsoup.nodes.Document soup = Jsoup.parse(html.toString());
        org.jsoup.select.Elements newHtml = soup.select(jsoup);
        if (newHtml != null) {
            Whitelist tags = new Whitelist();
            tags.addTags("div", "table", "tbody", "tr", "td", "p", "br", "ul", "li", "h1", "h2", "h3", "h4", "h5");
            tags.addAttributes("img", "src");
            tags.addAttributes("p", "style");
            String content = Jsoup.clean(newHtml.html(), tags);
            Document document = Jsoup.parse(content);
            Elements elements = document.select("img");
            for (Element element : elements) {
                String imagePath = element.attr("src");
                String imageLocalPath = url2LocalImage(imagePath);
                content = content.replace(imagePath, downServer + imageLocalPath);
            }
            return content;
        } else {
            return "";
        }
    }

    // jsoup过滤标签解析
    public String jsoupHtmlDownImageTag(Object html, String jsoup, String imageTag) {
        org.jsoup.nodes.Document soup = Jsoup.parse(html.toString());
        org.jsoup.select.Elements newHtml = soup.select(jsoup);
        if (newHtml != null) {
            Whitelist tags = new Whitelist();
            tags.addTags("div", "table", "tbody", "tr", "td", "p", "br", "ul", "li", "h1", "h2", "h3", "h4", "h5");
            tags.addAttributes("img", "src", imageTag);
            String content = Jsoup.clean(newHtml.html(), tags);
            Document document = Jsoup.parse(content);
            Elements elements = document.select("img");
            for (Element element : elements) {
                String imageSrc = element.attr("src");
                String imagePath = element.attr(imageTag);
                String imageLocalPath = url2LocalImage(imagePath);
                content = content.replace(imageSrc, downServer + imageLocalPath);
                content = content.replace(imagePath, downServer + imageLocalPath);
            }
            return content;
        } else {
            return "";
        }
    }


    // jsoup过滤标签解析
    public String jsoupHtmlDownImageAllTag(Object html, String jsoup) {
        org.jsoup.nodes.Document soup = Jsoup.parse(html.toString());
        org.jsoup.select.Elements newHtml = soup.select(jsoup);
        if (newHtml != null) {
            String content = newHtml.html();
            Document document = Jsoup.parse(content);
            Elements elements = document.select("img");
            for (Element element : elements) {
                String imagePath = element.attr("src");
                String imageLocalPath = url2LocalImage(imagePath);
                content = content.replace(imagePath, downServer + imageLocalPath);
            }
            return content;
        } else {
            return "";
        }
    }


    // jsoup解析
    public String jsoup(Object html, String jsoup) {
        org.jsoup.nodes.Document soup = Jsoup.parse(html.toString());
        org.jsoup.select.Elements newHtml = soup.select(jsoup);
        return newHtml.toString();
    }

    // 获取数组index值(其中index从1开始计数)
    public Object array(Object obj, int index) {
        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) (obj);
            if (array.size() >= index) {
                return array.get(index - 1);
            }
        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) (obj);
            if (array.length >= index) {
                return array[index - 1];
            }
        } else {
            try {
                JSONArray array = JSONArray.fromObject(obj);
                if (array.size() >= index) {
                    return array.get(index - 1);
                }
            } catch (Exception e) {
            }
        }
        return "";
    }

    // key-value值获取
    public Object keyVal(Object obj, String key) {
        Object value = null;
        if (obj instanceof JSONObject) {
            JSONObject json = (JSONObject) obj;
            value = json.get(key);
        } else {
            try {
                JSONObject json = JSONObject.fromObject(obj);
                value = json.get(key);
            } catch (Exception e) {
            }
        }
        return value == null ? "" : value;
    }

    // split分割
    @SuppressWarnings("rawtypes")
    public Object split(Object obj, String s) {
        if (obj == null) {
            return new ArrayList();
        }
        return obj.toString().split(s);
    }

    // 默认值
    public Object defval(Object obj, String defval) {
        if (obj == null || obj.toString().isEmpty()) {
            return defval;
        } else {
            return obj;
        }
    }

    // 系统时间long
    public String sysDateLong() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 数组字段串接字符串
    public String array2Str(Object obj, String field, String separator, int row) {
        String ret = "";
        if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = (row - 1) * 20; i < jsonArray.size() && i < row * 20; i++) {
                if (ret.length() > 0) {
                    ret += separator;
                }
                JSONObject json = JSONObject.fromObject(jsonArray.get(i));
                if (json.containsKey(field)) {
                    ret += json.getString(field);
                }
            }
        } else if (obj instanceof String) {
            try {
                JSONArray jsonArray = JSONArray.fromObject(obj);
                for (int i = (row - 1) * 20; i < jsonArray.size() && i < row * 20; i++) {
                    if (ret.length() > 0) {
                        ret += separator;
                    }
                    JSONObject json = JSONObject.fromObject(jsonArray.get(i));
                    if (json.containsKey(field)) {
                        ret += json.getString(field);
                    }
                }
            } catch (Exception e) {
            }
        }
        return ret;
    }

    // 时间戳转时间格式
    public String Long2FormatDate(String StrDate, String format) {
        Long longDate = new Long(StrDate);
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat(format);
        gc.setTimeInMillis(longDate.longValue());
        return df.format(gc.getTime());
    }

    public String url2LocalImage(String urlString) {
        try {
            CHttpClient cHttpClient = new CHttpClient();
            HttpUriRequest request = RequestBuilder.get().setUri(imageServer + urlString).build();
            HttpResponse httpResponse = cHttpClient.execute(request);
            String result = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = JSONObject.fromObject(result);
            return jsonObject.getString("path");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public int add(String var1, String var2) {
        return Integer.parseInt(var1) + Integer.parseInt(var2);

    }
}
