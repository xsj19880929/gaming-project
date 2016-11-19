package com.ygccw.website.database;

import java.util.List;

/**
 * @author soldier
 */
public class FindResultToSale<T> {

    private final List<T> list;
    private final int currentPage;
    private final int total;
    private final int totalPage;
    private final String pageName;

    public FindResultToSale(List<T> list, int total, int currentPage, int fetchSize, String pageName) {
        this.list = list;
        this.currentPage = currentPage;
        this.totalPage = (total / fetchSize) + ((total % fetchSize) != 0 ? 1 : 0);
        this.total = total;
        this.pageName = pageName;
    }

    public List<T> getList() {
        return list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotal() {
        return total;
    }


    public int getTotalPage() {
        return totalPage;
    }

    public String getPageName() {
        return pageName;
    }
}
