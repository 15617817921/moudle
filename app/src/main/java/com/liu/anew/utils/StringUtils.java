package com.liu.anew.utils;

/**
 * Created by Administrator on 2018/3/28.
 */

public class StringUtils {
    public StringUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断字符串是否null
     */
    public static String stringNull(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }
    /**
     * 判断字符串是否以,结尾，并去除
     */
    public static String stringSubEnds(String text) {
        if (text.endsWith(",")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
}
