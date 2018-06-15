package com.liu.anew.utils;

import android.text.TextUtils;
import android.util.Log;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间的工具。
 */
public class DateUtil {


    private static DateUtil util;
    private SimpleDateFormat formatter;
    private Calendar calendar;

    public static DateUtil getInstance() {
        if (util == null) {
            util = new DateUtil();
        }
        return util;

    }

    private DateUtil() {
        super();
    }

    public SimpleDateFormat dateFormater = new SimpleDateFormat(
            "yyyy/MM/dd");


    public Date getDate(String dateStr) {
        Date date = new Date();
        if (TextUtils.isEmpty(dateStr)) {
            return date;
        }
        try {
            date = dateFormater.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Calendar getCalendar(String dateStr) {
        // 声明一个Date类型的对象
        Date date = null;
        // 声明格式化日期的对象
        SimpleDateFormat format = null;
        if (dateStr != null) {
            // 创建日期的格式化类型
            format = new SimpleDateFormat("yyyy/MM/dd");
            // 创建一个Calendar类型的对象
            calendar = Calendar.getInstance();
            // forma.parse()方法会抛出异常
            try {
                //解析日期字符串，生成Date对象
                date = format.parse(dateStr);
                // 使用Date对象设置此Calendar对象的时间
                calendar.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return calendar;
    }

    /**
     * 时间
     * 年月日时分秒
     *
     * @return
     */
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }

    /**
     * 时间
     * 年月日
     *
     * @return
     */
    public String getYear_Day() {
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String date = formatter.format(curDate);
//        String nyr = date.substring(0, date.length() - 8);
        return date;
    }

    public String getTimeToDate(String time) {//14位20170906 101230

        String n = time.substring(0, 4);
        String y = time.substring(4, 6);
        String r = time.substring(6, 8);
        String s = time.substring(8, 10);
        String f = time.substring(10, 12);
        String m = time.substring(12, time.length());
        return n + "-" + y + "-" + r + " " + s + ":" + f + ":" + m;
    }

    public String getTime() {//14位
        formatter = new SimpleDateFormat("yyyy/MM/ddHH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String date = formatter.format(curDate);

        String s1 = date.replace("/", "");
        String s2 = s1.replace(":", "");
        return s2.trim();
    }

    /**
     * 时间
     * 时分秒
     *
     * @return
     */
    public String getHour_s() {
        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String date = formatter.format(curDate);
        String sfm = date.substring(date.length() - 8, date.length());

        return sfm;
    }
    /**
     * 时间
     * 时分秒
     *
     * @return
     */
    public String getNian_miao() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSSS");
        String hm =formatter.format(new Date());

        return hm;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = formatter.parse(str1);
            dt2 = formatter.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }
}
