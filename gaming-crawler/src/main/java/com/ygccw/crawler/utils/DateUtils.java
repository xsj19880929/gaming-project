package com.ygccw.crawler.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author soldier
 */
public class DateUtils {
    public static String formatToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);

    }

    public static void main(String[] args) {
        System.out.println(formatToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
