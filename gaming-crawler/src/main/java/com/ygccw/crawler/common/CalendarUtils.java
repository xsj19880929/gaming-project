package com.ygccw.crawler.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
    private static final String DATE_FORMAT_DATE = "yyyy-MM-dd";
    private static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String date, String formatPattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatPattern);
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String format(Date date, String formatPattern) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    public static Date getDayEndTime(Date date) {
        return parse(format(date, DATE_FORMAT_DATE) + " 23:59:59", DATE_FORMAT_DATETIME);
    }

    public static String getTimeDay() {
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        return dateFormat.format(new Date());
    }

    public static Date getDaysDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return parse(format(c.getTime(), DATE_FORMAT_DATETIME), DATE_FORMAT_DATETIME);
    }
}
