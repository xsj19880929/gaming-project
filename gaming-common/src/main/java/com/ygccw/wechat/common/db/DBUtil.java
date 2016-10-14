package com.ygccw.wechat.common.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DBUtil {

    /**
     * 将以下划线分隔的多个全部为大写字母的单词转换为JAVA BEAN的命名格式的单词
     * <p/>
     * 如：TOWN_NAME将转换为townName
     *
     * @param fields 大写的由多个单词组成的字段名
     * @return JAVA BEAN格式的单词
     */
    public static String toBeanField(String fields) {
        String field = fields;
        field = field.toLowerCase();
        String[] words = field.split("_");
        StringBuilder result = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            result.append(word.substring(0, 1).toUpperCase());
            if (word.length() > 1) {
                result.append(word.substring(1, word.length()));
            }
        }
        return result.toString();
    }


    /**
     * 获取JDBC记录集的列名
     *
     * @param rs          JDBC记录集
     * @param useBeanName 是否使用JAVA BEAN的命名格式返回字段名 JAVA BEAN的命名格式为：townName
     * @return 记录集中所有的字段名
     */
    public static String[] getColumnNames(ResultSet rs, boolean useBeanName) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            String[] colNames = new String[colCount];

            for (int i = 0; i < colCount; i++) {
                String col = meta.getColumnLabel(i + 1);
                if (useBeanName) {
                    col = toBeanField(col);
                }
                colNames[i] = col;
            }

            return colNames;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{};
        }
    }


}
