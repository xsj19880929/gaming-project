package com.ygccw.website.utils;

/**
 * @author soldier
 */
public class PageUtils {
    public static int getPageSize(int total, int fetchSize) {
        return (total / fetchSize) + ((total % fetchSize) != 0 ? 1 : 0);
    }

    public static int getStartRecord(int currentPage, int fetchSize) {
        return (currentPage - 1) * fetchSize;
    }
}
