package com.my.service;

import com.my.bean.Column;
import com.my.utils.JdbcUtil;
import com.my.utils.StringTools;
import com.my.utils.TableConvert;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库列操作
 */
public class ColumnService {

    public List<Column> getColumnList(String tableName) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        String sql = "select column_name, data_type, column_comment, 0, 0, character_maximum_length, is_nullable nullable from information_schema.columns where table_name = ? and table_schema = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, tableName);
        ps.setString(2, JdbcUtil.DATABASE);

        ResultSet rs = ps.executeQuery();

        List<Column> columnList = new ArrayList<>();
        while (rs.next()) {
            Column column = new Column();
            column.setColumnName(rs.getString(1));
            column.setPropertyName(StringTools.columnToProperty(rs.getString(1)));
            column.setColumnType(rs.getString(2));
            column.setColumnComment(rs.getString(3));
            column.setPrecision(rs.getString(4));
            column.setScale(rs.getString(5));
            column.setCharmaxLength((rs.getString(6) == null) ? "" : rs.getString(6));
            column.setNullable(TableConvert.getNullAble(rs.getString(7)));
            column.setDataType(getType(rs.getString(2), column.getPrecision(), column.getScale()));
            columnList.add(column);
        }
        JdbcUtil.free(rs, ps, connection);
        return columnList;
    }

    private String getType(String dataType, String precision, String scale) {
        dataType = dataType.toLowerCase();
        if (dataType.contains("char"))
            dataType = "String";
        else if (dataType.contains("int"))
            dataType = "Integer";
        else if (dataType.contains("float"))
            dataType = "Float";
        else if (dataType.contains("double"))
            dataType = "Double";
        else if (dataType.contains("number"))
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                dataType = "BigDecimal";
            else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
                dataType = "Long";
            else
                dataType = "Integer";

        else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "java.util.Date";
        else if (dataType.contains("time"))
            dataType = "java.sql.Timestamp";
        else if (dataType.contains("clob"))
            dataType = "java.sql.Clob";
        else if (dataType.contains("text"))
            dataType = "String";
        else
            dataType = "java.lang.Object";

        return dataType;
    }


}
