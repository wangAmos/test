package com.my.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 拼装字符串
 */
public class WriteFactoryBean {

    /**
     * 根据列名得到属性
     *
     * @param columns 列
     * @return 属性拼成的
     */
    public String getFields(List<Column> columns) {
        return columns.stream()
                      .map(e -> String.format("\r\tprivate %s %s; //%s", e.getDataType(), e.getPropertyName(), e.getColumnComment()))
                      .reduce("", String::concat);
    }

    /**
     * 获取数据库的列字符串 用,隔开
     *
     * @param columns 列
     * @return 字符串隔开的列
     */
    private String getColumnList(List<Column> columns) {
        return columns.stream()
                      .map(Column::getColumnName)
                      .collect(Collectors.joining(","));
    }

/**
     * 获取数据库的列字符串 用,隔开
     *
     * @param columns 列
     * @return 字符串隔开的列
     */
    private String getPropertyList(List<Column> columns) {
        return columns.stream()
                      .map(Column::getPropertyName)
                      .collect(Collectors.joining(","));
    }

    /**
     * 根据id删除sql
     *
     * @param tableName 表名
     * @param idColumn id列
     * @return deleteById sql语句
     */
    private String getDeleteByIdSql(String tableName, Column idColumn) {
        return String.format("delete from %s where %s = #{%s}", tableName, idColumn.getColumnName(), idColumn.getPropertyName());
    }

    /**
     * 根据id获取对象
     *
     * @param tableName 表名
     * @param idColumn id 列
     * @return getById sql 语句
     */
    private String getGetByIdSql(String tableName, Column idColumn) {
        return String.format("select <include refid=\"Base_Column_List\" /> from %s where %s = #{%s}", tableName, idColumn.getColumnName(), idColumn.getPropertyName());
    }

    /**
     * update 语句
     *
     * @param tableName 表名称
     * @param columns 列
     * @return update sql 语句
     */
    private String getUpdateSql(String tableName, List<Column> columns) {
        String str = columns.stream()
               .filter(e -> !"CREATETIME".equals(e.getPropertyName().toUpperCase())) //createTime 不需要更新
               .map(e -> "UPDATETIME".equals(e.getPropertyName().toUpperCase()) //updatetime 设置为now()
                       ? String.format("%s = now()",e.getPropertyName())
                       : String.format("%s = #{%s}", e.getColumnName(), e.getPropertyName()))
               .collect(Collectors.joining(","));
        return String.format("update %s set %s where %s = #{%s}", tableName, str, columns.get(0).getColumnName(), columns.get(0).getPropertyName())
                     .replace("#{createTime}", "now()")
                     .replace("#{updateTime}", "now()");
    }

    /**
     * updateBySelective sql 语句
     *
     * @param tableName 表名
     * @param columns 列
     * @return updateBySelective sql 语句
     */
    private String getUpdateBySelectiveSql(String tableName, List<Column> columns) {
        String str = columns.stream()
                .map(e -> "String".equals(e.getDataType())
                            ? String.format("\t<if test=\"%s != null and %s != null\">\n\t\t%s = #{%s},\n\t</if>\n", e.getPropertyName(), e.getPropertyName(), e.getColumnName(), e.getPropertyName())
                            : String.format("\t<if test=\"%s != null\">\n\t\t%s = #{%s},\n\t</if>\n", e.getPropertyName(), e.getColumnName(), e.getPropertyName()))
                .collect(Collectors.joining());
        return String.format("update %s \n\t<set>\n%s\t</set> where %s = #{%s}", tableName, str, columns.get(0).getColumnName(), columns.get(0).getPropertyName());
    }

    /**
     * insert 语句
     * @param tableName 表名
     * @param columns 列
     * @return insert sql语句
     */
    private String getInsertSql(String tableName, List<Column> columns) {
        return String.format("insert into %s (%s) values (#{%s})", tableName, getColumnList(columns), getPropertyList(columns).replaceAll(",", "},#{"))
                     .replace("#{createTime}", "now()")
                     .replace("#{updateTime}", "now()");
    }

    public Map<String, Object> getAutoCreateSql(String tableName, List<Column> columns) {
        Map<String, Object> sqlMap = new HashMap<>();
        sqlMap.put("columnFields", getColumnList(columns));
        sqlMap.put("insert", getInsertSql(tableName, columns));
        sqlMap.put("update", getUpdateSql(tableName, columns));
        sqlMap.put("deleteById", getDeleteByIdSql(tableName, columns.get(0)));
        sqlMap.put("updateSelective", getUpdateBySelectiveSql(tableName, columns));
        sqlMap.put("getById", getGetByIdSql(tableName, columns.get(0)));
        return sqlMap;
    }

}
