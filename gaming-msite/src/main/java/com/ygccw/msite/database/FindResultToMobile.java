package com.ygccw.msite.database;

import java.util.List;

/**
 * @author soldier
 */
public class FindResultToMobile<T> {
    private final List<T> list;
    private final int fetchSize;
    private final String pageName;

    public FindResultToMobile(List<T> list, int fetchSize, String pageName) {
        this.list = list;
        this.fetchSize = fetchSize;
        this.pageName = pageName;
    }

    public List<T> getList() {
        return list;
    }

    public String getPageName() {
        return pageName;
    }

    public int getFetchSize() {
        return fetchSize;
    }
}
