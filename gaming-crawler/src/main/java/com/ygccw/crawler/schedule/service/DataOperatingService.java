package com.ygccw.crawler.schedule.service;


import com.ygccw.crawler.common.NativeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

@Service
public class DataOperatingService {
    private final Logger logger = LoggerFactory.getLogger(DataOperatingService.class);
    private NativeDao nativeDao;

    @Inject
    public void setNativeDao(NativeDao nativeDao) {
        this.nativeDao = nativeDao;
    }

    public void mergeDataMD5(HashMap<String, List<HashMap<String, String>>> results) throws Exception {
        // 循环写入
        for (Entry<String, List<HashMap<String, String>>> entry : results.entrySet()) {
            String table = entry.getKey();
            if (table.endsWith("sametable")) {
                table = table.substring(0, table.length() - 12);
            }
            List<HashMap<String, String>> mapList = entry.getValue();
            for (HashMap<String, String> map : mapList) {
                String keyName = map.get("keyName");
                String keyValue = map.get("keyValue");
                if (selectData(map.get("keyName"), map.get("keyValue"), table)) {
                    if (map.get("md5") != null && !selectData("md5", map.get("md5"), table)) {
                        this.nativeDao.getJdbcTemplate().execute(updateData(map, table, keyName, keyValue));
                    }
                } else {
                    this.nativeDao.getJdbcTemplate().execute(insertData(map, table));
                }
            }
        }

    }


    public void mergeData(HashMap<String, List<HashMap<String, String>>> results) throws Exception {
        // 循环写入
        for (Entry<String, List<HashMap<String, String>>> entry : results.entrySet()) {
            String table = entry.getKey();
            if (table.endsWith("sametable")) {
                table = table.substring(0, table.length() - 12);
            }
            List<HashMap<String, String>> mapList = entry.getValue();
            for (HashMap<String, String> map : mapList) {
                String keyName = map.get("keyName");
                String keyValue = map.get("keyValue");
                if (selectData(map.get("keyName"), map.get("keyValue"), table)) {
                    this.nativeDao.getJdbcTemplate().execute(updateData(map, table, keyName, keyValue));
                } else {
                    this.nativeDao.getJdbcTemplate().execute(insertData(map, table));
                }
            }
        }

    }

    public void mergeDataJumpKey(HashMap<String, List<HashMap<String, String>>> results, String jumpKey) throws Exception {
        // 循环写入
        for (Entry<String, List<HashMap<String, String>>> entry : results.entrySet()) {
            String table = entry.getKey();
            if (table.endsWith("sametable")) {
                table = table.substring(0, table.length() - 12);
            }
            List<HashMap<String, String>> mapList = entry.getValue();
            for (HashMap<String, String> map : mapList) {
                String keyName = map.get("keyName");
                String keyValue = map.get("keyValue");
                if (!selectData(jumpKey, map.get(jumpKey), table)) {
                    if (selectData(map.get("keyName"), map.get("keyValue"), table)) {
                        this.nativeDao.getJdbcTemplate().execute(updateData(map, table, keyName, keyValue));
                    } else {
                        this.nativeDao.getJdbcTemplate().execute(insertData(map, table));
                    }
                }
            }
        }

    }

    public void updateData(HashMap<String, List<HashMap<String, String>>> results) throws Exception {
        // 循环写入
        for (Entry<String, List<HashMap<String, String>>> entry : results.entrySet()) {
            String table = entry.getKey();
            if (table.endsWith("sametable")) {
                table = table.substring(0, table.length() - 12);
            }
            List<HashMap<String, String>> mapList = entry.getValue();
            for (HashMap<String, String> map : mapList) {
                String keyName = map.get("keyName");
                String keyValue = map.get("keyValue");
                this.nativeDao.getJdbcTemplate().execute(updateData(map, table, keyName, keyValue));
            }
        }

    }


    // 插入
    private String insertData(HashMap<String, String> map, String table) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into " + table + "(");
        for (Entry<String, String> mapEntry : map.entrySet()) {
            if (!"keyName".equals(mapEntry.getKey().toString()) && !"keyValue".equals(mapEntry.getKey().toString()) && !"jumpData".equals(mapEntry.getKey().toString())) {
                String mapKey = mapEntry.getKey();
                sql.append(mapKey).append(",");
            }
        }
        sql.replace(sql.length() - 1, sql.length(), "");
        sql.append(") values(");
        for (Entry<String, String> mapEntry : map.entrySet()) {
            if (!"keyName".equals(mapEntry.getKey().toString()) && !"keyValue".equals(mapEntry.getKey().toString()) && !"jumpData".equals(mapEntry.getKey().toString())) {
                String mapValue = mapEntry.getValue();
                sql.append("'" + mapValue + "'").append(",");
            }
        }
        sql.replace(sql.length() - 1, sql.length(), "");
        sql.append(")");
        logger.info(sql.toString());
        return sql.toString();
    }

    // 更新
    private String updateData(HashMap<String, String> map, String table, String keyName, String keyValue) {
        StringBuffer sql = new StringBuffer();
        sql.append("update " + table + " set ");
        for (Entry<String, String> mapEntry : map.entrySet()) {
            if (!"keyName".equals(mapEntry.getKey().toString()) && !"keyValue".equals(mapEntry.getKey().toString()) && !"id".equals(mapEntry.getKey().toString())
                    && !"created_time".equals(mapEntry.getKey().toString()) && !"jumpData".equals(mapEntry.getKey().toString())) {
                String mapKey = mapEntry.getKey();
                String mapValue = mapEntry.getValue();
                sql.append(mapKey).append("='" + mapValue + "',");
            }
        }
        sql.replace(sql.length() - 1, sql.length(), "");
        sql.append(" where " + keyName + "='" + keyValue + "'");
        logger.info(sql.toString());
        return sql.toString();
    }

    public boolean selectData(String keyName, String keyValue, String table) {
        String sql = "select count(id) from " + table + " where " + keyName + "='" + keyValue + "'";
        int count = this.nativeDao.getJdbcTemplate().queryForInt(sql);
        if (count > 0) {
            return true;
        }
        return false;
    }

}
