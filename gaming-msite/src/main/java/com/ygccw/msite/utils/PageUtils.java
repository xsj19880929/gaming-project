package com.ygccw.msite.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getPageUrl(HttpServletRequest request) {
        if (request != null) {
            String url = request.getRequestURI();
            Pattern pattern = Pattern.compile("(.*)_\\d*.html");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            }
            Pattern pattern2 = Pattern.compile("(.*).html");
            Matcher matcher2 = pattern2.matcher(url);
            if (matcher2.find()) {
                return matcher2.group(1);
            }
        }
        return null;

    }
}
