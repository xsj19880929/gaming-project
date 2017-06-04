package com.ygccw.crawler.common;

import com.google.common.io.Resources;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class Records {
    public List<String> names = new ArrayList<String>();
    public List<String> ids = new ArrayList<String>();
    public List<String> fields = new ArrayList<String>();
    public List<String> values = new ArrayList<String>();
}


/**
 * 模板装载与测试
 */
public class TemplateUtil {
    private static HashMap<String, JSONObject> extractionRules = new HashMap<String, JSONObject>();

    public static void main(String[] args) {
//        String a = "<p>蚕豆网LOL交流群：148788322(点击加入)</p>";
//
//        a = a.replaceAll("<p>蚕豆网([\\u4E00-\\u9FA5]|\\w)+?交流群：\\d+?\\(点击加入\\)</p>", "11");
//
//        System.out.println(a);
        LoadXML();
    }

    public static JSONObject getExtractionRules(String pageType) {
        if (extractionRules == null || extractionRules.isEmpty()) {
            try {
                LoadXML();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extractionRules.get(pageType);
    }

    private static void LoadXML() {
        try {
            URL url = Resources.getResource("tpl");
//            URL url = TemplateUtil.class.getProtectionDomain().getCodeSource().getLocation();
            String path = url.toString();
//            int index = path.indexOf("classes");
//            path = path.substring(0, index);
            if (path.startsWith("file")) {
                path = path.substring(6);
            }
            if (path.startsWith("opt")) {
                path = "/" + path;
            }
            // System.out.println("模板路径：" + path + "classes/tpl");
            File dir = new File(path);
//            File dir = new File(path + "classes/tpl");
            if (dir.exists()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.getPath().endsWith(".xml")) {
                        Document doc = new SAXReader().read(file);
                        if (doc != null) {
                            Element root = doc.getRootElement();
                            String id = root.attributeValue("pageType");
                            JSONObject job = new JSONObject();

                            for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
                                Element rows = (Element) i.next();
                                JSONObject jobi = new JSONObject();
                                jobi.put("objectName", rows.attributeValue("objectName"));
                                if (rows.attributeValue("flag") != null) {
                                    jobi.put("flag", rows.attributeValue("flag"));
                                }
                                if (rows.element("rows") != null) {
                                    jobi.put("rows", rows.element("rows").getText().trim());
                                }
                                JSONArray arry = new JSONArray();

                                for (Iterator<?> j = rows.elementIterator("uiobject"); j.hasNext(); ) {
                                    Element row = (Element) j.next();
                                    JSONObject jobj = new JSONObject();
                                    jobj.put("fieldName", row.attributeValue("fieldName"));
                                    jobj.put("flag", row.attributeValue("flag"));
                                    jobj.put("ruleName", row.attributeValue("ruleName"));
                                    if (row.attributeValue("nullAble") != null) {
                                        jobj.put("nullAble", row.attributeValue("nullAble"));
                                    }
                                    if (row.element("expression") != null) {
                                        jobj.put("expression", row.element("expression").getText().trim());
                                    }
                                    arry.add(jobj);
                                }
                                job.put(jobi.toString(), arry);
                            }
                            extractionRules.put(id, job);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
