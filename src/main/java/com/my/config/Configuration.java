package com.my.config;

/**
 * 系统配置信息
 */
public class Configuration {
    public static String projectPath;
    public static final String templatePath;
    public static final String systemEncoding = "UTF-8";
    public static final String basePackage = "com.my";
    public static final String target;
    public static String KEY_TYPE_01 = "01"; //UUID
    public static String KEY_TYPE_02 = "02"; //自增主键

    static {
        projectPath = System.getProperty("user.dir").replace("\\", "/");
        templatePath = projectPath + "/src/main/resources/template";
        target = projectPath + "/target";
    }

    private Configuration(){}

}
