package com.ygccw.crawler.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.ListOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 解析组件
 */
public class Semantic {

    /**
     * 解析主处理
     *
     * @param pageType
     * @param variables
     * @return
     */
    private final Logger logger = LoggerFactory.getLogger(Semantic.class);

    @SuppressWarnings("rawtypes")
    public HashMap<String, List<HashMap<String, String>>> semanticPage(String pageType, List<Variable> variables, List<JSONObject> nextTasks) throws Exception {
        // logger.info("开始");
        long startRulePage = System.currentTimeMillis();
        HashMap<String, List<HashMap<String, String>>> results = new HashMap<String, List<HashMap<String, String>>>();
        JSONObject jsonRules = TemplateUtil.getExtractionRules(pageType);
        // logger.info("模板是否为空");
        if (jsonRules == null || jsonRules.size() == 0) {
            return results;
        }
        // logger.info("模板不为空");
        // 循环模板数据块

        Iterator iteRule = jsonRules.entrySet().iterator();
        while (iteRule.hasNext()) {
            List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();
            ListOrderedMap.Entry entryRule = (Entry) iteRule.next();
            JSONObject jsonRule = JSONObject.fromObject(entryRule.getKey());
            JSONArray fieldRules = JSONArray.fromObject(entryRule.getValue());
            // logger.info("准备解析");
            // 预解析数据块
            if (jsonRule.getString(Constants.TPL_OBJECT_NAME).equals(Constants.TPL_BEFORE_SEMANTIC)) {
                // logger.info("开始预解析");
                beforeSemantic(fieldRules, variables);
                continue;
            }
            // 获取页面记录条数
            int rows = 1;
            if (jsonRule.containsKey(Constants.TPL_ROWS)) {
                String expression = jsonRule.getString(Constants.TPL_ROWS);
                rows = (Integer) ExpressionEvaluator.evaluate(expression, variables);
            }
            variables.add(Variable.createVariable(Constants.TPL_ROWS, rows));

            // 循环记录数
            for (int row = 1; row <= rows; row++) {
                boolean jumpData = false;
                variables.add(Variable.createVariable("row", row));
                HashMap<String, String> map = new HashMap<String, String>();
                // logger.info("开始循环抽取");
                // 循环抽取一条记录的各个字段值
                for (int j = 0; j < fieldRules.size(); j++) {
                    JSONObject myRule = JSONObject.fromObject(fieldRules.get(j));
                    if (myRule.containsKey(Constants.TPL_EXPRESSION)) {
                        String expression = myRule.getString(Constants.TPL_EXPRESSION);
                        variables.add(Variable.createVariable("map", JSONObject.fromObject(map)));
                        Object obj = ExpressionEvaluator.evaluate(expression, variables);
                        // logger.info("字段数据：{} {} ", expression, obj);
                        if (myRule.getString(Constants.TPL_FIELD_NAME).endsWith("Semantic")) {
                            variables.add(Variable.createVariable(myRule.getString(Constants.TPL_FIELD_NAME), obj));
                        }
                        if (obj != null && !obj.toString().isEmpty() && !myRule.getString(Constants.TPL_FIELD_NAME).endsWith("Semantic")) {
                            map.put(myRule.getString(Constants.TPL_FIELD_NAME), obj.toString().trim());
                        }
                        if (map.get("jumpData") != null && "YES".equals(map.get("jumpData"))) {
                            jumpData = true;
                        }
                    }
                }
                if (!checkResult(pageType, jsonRule.getString(Constants.TPL_OBJECT_NAME), fieldRules, map) && jsonRule.getString(Constants.TPL_OBJECT_NAME).equals("nextTask") && nextTasks != null) {
                    nextTasks.add(JSONObject.fromObject(JSONObject.fromObject(map)));
                    continue;
                }
                // 验证数据完整性
                if (!checkResult(pageType, jsonRule.getString(Constants.TPL_OBJECT_NAME), fieldRules, map) && !jumpData) {
                    listMap.add(map);
                }
            }
            if (listMap.size() > 0) {
                results.put(jsonRule.getString(Constants.TPL_OBJECT_NAME), listMap);
            }
            variables.add(Variable.createVariable("results", results));
        }
        long endRulePage = System.currentTimeMillis();
        logger.info("解析内容耗时：" + (endRulePage - startRulePage) + "ms\t\t" + pageType);
        return results;
    }

    /**
     * 预解析
     *
     * @param arrayRules
     * @param variables
     */
    private void beforeSemantic(JSONArray arrayRules, List<Variable> variables) {
        for (int j = 0; j < arrayRules.size(); j++) {
            JSONObject myRule = JSONObject.fromObject(arrayRules.get(j));
            if (myRule.containsKey(Constants.TPL_EXPRESSION)) {
                String expression = myRule.getString(Constants.TPL_EXPRESSION);
                Object obj = ExpressionEvaluator.evaluate(expression, variables);
                variables.add(Variable.createVariable(myRule.getString(Constants.TPL_FIELD_NAME), obj));
                // logger.info("预编译数据：{} {} ", expression, obj);
            }
        }
    }

    /**
     * 验证数据完整性
     *
     * @param pageType
     * @param jsonRules
     * @param results
     * @return
     */
    public boolean checkResult(String pageType, String objectName, JSONArray arrayRules, HashMap<String, String> map) {

        for (int i = 0; i < arrayRules.size(); i++) {
            JSONObject myRule = JSONObject.fromObject(arrayRules.get(i));
            if (myRule.containsKey(Constants.TPL_NULLABLE) && myRule.getString(Constants.TPL_NULLABLE).equals("false")) {
                String fieldName = myRule.getString(Constants.TPL_FIELD_NAME);
                String value = map.get(fieldName);
                if (value == null || value.isEmpty()) {
                    String error = objectName + "\n\t" + fieldName + "字段为空";
                    logger.info("{} ", error);
                    return true;
                }
            }
        }

        return false;
    }

}
