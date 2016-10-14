package com.ygccw.wechat.common.db;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RowMapper4Map implements RowMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        String[] colNames = DBUtil.getColumnNames(rs, true);
        Map<String, Object> rt = new HashMap<>();
        for (int i = 0; i < colNames.length; i++) {
            String colName = colNames[i];
            rt.put(colName, rs.getObject(i + 1));
        }
        return rt;
    }

}
