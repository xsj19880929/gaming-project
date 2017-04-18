package com.ygccw.crawler.common;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据标准化工具
 *
 * @author wangyz
 */
public class MyParser {
    public static Map<String, Pattern> patterns = new WeakHashMap<String, Pattern>();
    private static Tidy jtidy = null;
    private static int counter = 0;

    /**
     * 获取tidy对象
     *
     * @return
     */
    public static Tidy getTidy() {
        Tidy jtidy = new Tidy();
        jtidy.setQuiet(true);
        jtidy.setShowWarnings(false);
        jtidy.setXmlOut(true);
        // jtidy.setInputEncoding("ISO-8859-1");
        jtidy.setOutputEncoding("UTF-8");
        jtidy.setDropFontTags(true);
        jtidy.setDropEmptyParas(true);
        jtidy.setFixComments(true);
        jtidy.setFixBackslash(true);
        jtidy.setMakeClean(true);
        jtidy.setQuoteNbsp(false);
        jtidy.setQuoteMarks(false);
        jtidy.setQuoteAmpersand(true);
        jtidy.setTrimEmptyElements(false);
        return jtidy;
    }

    /**
     * 提取第n个匹配项
     *
     * @param value
     * @param regexp
     * @param g
     * @return
     */
    public static String Extract(String value, String regexp, int g) {

        if (regexp == null || regexp.isEmpty())
            return value;

        // 取正则表达式
        Pattern p = patterns.get(regexp);
        if (p == null) {
            p = Pattern.compile(regexp);
            patterns.put(regexp, p);
        }

        // 匹配
        Matcher m = p.matcher(value);
        if (m.find()) {
            return m.group(g);
        } else {
            return null;
        }
    }

    /**
     * 提取所有匹配项
     *
     * @param value
     * @param regexp
     * @return
     */
    public static String Extract(String value, String regexp) {

        if (regexp == null || regexp.isEmpty()) {
            return value;
        }

        Pattern p = patterns.get(regexp);
        if (p == null) {
            p = Pattern.compile(regexp);
            patterns.put(regexp, p);
        }

        Matcher m = p.matcher(value);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

    /**
     * html标准化清理
     *
     * @param url
     * @param html
     * @return
     */
    public static String prepare(String url, String html) {
        org.jsoup.nodes.Document soup = Jsoup.parse(html);
        org.jsoup.select.Elements links = soup.select("a[href]");
        for (org.jsoup.nodes.Element link : links) {
            String absUrl = MyParser.absolutePath(url, link.attr("href"));
            String encodedUrl = absUrl;
            try {
                encodedUrl = MyParser.encodeUrl(absUrl);
            } catch (Exception e) {
                encodedUrl = absUrl;
            }
            link.attr("href", encodedUrl);
        }
        org.jsoup.select.Elements scripts = soup.select("script");
        for (org.jsoup.nodes.Element script : scripts) {
            script.text("");
        }
        return soup.html();
    }

    /**
     * url编码处理
     *
     * @param url
     * @return
     */
    public static String encodeUrl(String url) {
        StringBuilder dest = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if ((c > 0x7e) || (c <= 0x20) || (c == '<') || (c == '>')) {
                if (c < 0xff)
                    dest.append('%').append(Integer.toHexString(c).toUpperCase());
                else {
                    byte[] ba = new byte[3];
                    ba[0] = (byte) ((c & 0x00F000) >> 12 | 0xE0);
                    ba[1] = (byte) ((c & 0x000FC0) >> 6 | 0x80);
                    ba[2] = (byte) ((c & 0x00003F) | 0x80);
                    dest.append('%').append(byte2HexString(ba[0]));
                    dest.append('%').append(byte2HexString(ba[1]));
                    dest.append('%').append(byte2HexString(ba[2]));
                }
            } else {
                dest.append(c);
            }
        }
        return dest.toString();
    }

    private static String byte2HexString(byte b) {
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] newChar = new char[2];
        newChar[0] = hex[(b & 0xf0) >> 4];
        newChar[1] = hex[b & 0xf];

        return new String(newChar);
    }

    /**
     * url全路径拼接
     *
     * @param site
     * @param part
     * @return
     */
    public static String absolutePath(String site, String part) {
        site = site.trim();
        part = part.trim();
        if (part.indexOf("http://") > -1) {
            return part;
        }
        StringBuilder s = new StringBuilder();
        if (part.startsWith("./")) {
            part = part.substring(1);
            int end = site.lastIndexOf("/");
            s.append(site.substring(0, end));
        } else if (part.indexOf("/") != 0) {
            int end = site.lastIndexOf("/");
            s.append(site.substring(0, end));
            s.append("/");
        } else {
            s.append(MyParser.Extract(site, "(http://[^/]+)", 1));
        }
        s.append(part);
        return s.toString();
    }

    /**
     * 根据网页类型转化html->tidyDom
     *
     * @param html
     * @return
     */
    public static Document dealHtml(String html) {
        Document tidyDom = null;
        try {
            if (isXml(html)) {
                tidyDom = parseDom(html);
            } else {
                if (jtidy == null || counter++ > 10000) {
                    jtidy = new Tidy();
                    jtidy.setQuiet(true);
                    jtidy.setShowWarnings(false);
                    jtidy.setTrimEmptyElements(false);
                    counter = 0;
                }
                tidyDom = jtidy.parseDOM(new StringReader(html), null);
                // toStringFromDoc(tidyDom);
                // SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                // File file = new File("d:\\" + "tidy_" + df.format(new Date())
                // + ".html");
                // FileOutputStream fs = new FileOutputStream(file);
                // jtidy.pprint(tidyDom, fs);
                // fs.close();
//                writerData(toStringFromDoc(tidyDom), "d:\\tidy111.xml");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tidyDom;
    }

    public static String toStringFromDoc(Document document) {
        String result = null;

        if (document != null) {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try {
                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                // text
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(new DOMSource(document.getDocumentElement()), strResult);
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
            try {
                strWtr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void writerData(String info, String fileName) {
        try {
            File file = new File(fileName);
            if (!file.isFile() && !file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), true), "UTF-8"));
            bufferedWriter.write(info);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * xml判断
     *
     * @param s
     * @return
     */
    public static boolean isXml(String s) {
        return s.startsWith("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
    }

    /**
     * html->tidyDom转化
     *
     * @param html
     * @return
     */
    public static Document parseDom(String s) throws ParserConfigurationException, SAXException, IOException {
        org.dom4j.Document doc = null;
        try {
            doc = DocumentHelper.parseText(s);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        StringReader reader = new StringReader(doc.asXML());
        org.xml.sax.InputSource source = new org.xml.sax.InputSource(reader);
        javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return (documentBuilder.parse(source));
    }

}
