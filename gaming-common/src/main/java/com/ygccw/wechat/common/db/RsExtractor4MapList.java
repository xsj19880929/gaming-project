package com.ygccw.wechat.common.db;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RsExtractor4MapList implements
        ResultSetExtractor<List<Map<String, Object>>> {

    public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Map<String, Object>> array = new ArrayList<>();
        String[] colNames = DBUtil.getColumnNames(rs, true);
        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 0; i < colNames.length; i++) {
                String colName = colNames[i];
                record.put(colName, rs.getObject(i + 1));
            }
            array.add(record);
        }

        return array;
    }

}
