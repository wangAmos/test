package com.my.utils;

import java.util.Arrays;

/**
 * 字符串工具
 */
public class StringTools {

    private StringTools() {}

    /**
     * 将表名转换为类名
     * @param table 表名
     * @return 类名
     */
    public static String tableToClass(String table) {
        return Arrays.stream(table.split("_"))
                     .map(e -> e.substring(0, 1).toUpperCase() + e.substring(1, e.length()))
                     .reduce("", String::concat);
    }

    /**
     * 将列名转换为属性名
     * @param column 列名
     * @return 属性名
     */
    public static String columnToProperty(String column) {
        String str = tableToClass(column);
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 将表名转换为类名
     * @param className 类名
     * @return 类名转第一个字母小写的类名
     */
    public static String classToInstance(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
    }

}
