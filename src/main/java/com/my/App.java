package com.my;

import com.my.bean.Column;
import com.my.bean.WriteFactoryBean;
import com.my.common.CommonPageParser;
import com.my.config.Configuration;
import com.my.service.ColumnService;
import com.my.utils.StringTools;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.List;

/**
 * 程序入口
 */
public class App {

    public static void main(String[] args) throws Exception {

        codeGenerate("sms_send_info");

    }
    

    public static void codeGenerate(String tableName) throws Exception {
        ColumnService columnService = new ColumnService();
        List<Column> columns = columnService.getColumnList(tableName); //所有的列

        WriteFactoryBean factoryBean = new WriteFactoryBean();
        String className = StringTools.tableToClass(tableName);

        String keyType = columns.get(0).getDataType().equals("Integer") ? Configuration.KEY_TYPE_02 : Configuration.KEY_TYPE_01;

        VelocityContext context = new VelocityContext();
        context.put("className", className);
        context.put("lowerName", StringTools.classToInstance(className));
        context.put("tableName", tableName);
        context.put("basePackage", Configuration.basePackage);
        context.put("keyType", keyType);
        context.put("feilds", factoryBean.getFields(columns));
        context.put("SQL", factoryBean.getAutoCreateSql(tableName, columns));
        context.put("columnDatas", columns);

        String pckPath = Configuration.target;
        String beanPath = File.separator + "entity" + File.separator  + className + ".java";
        String mapperPath = File.separator + "mapper" + File.separator + className + "Mapper.java";
        String servicePath = File.separator + "service" + File.separator + className + "Service.java";
        String serviceImplPath = File.separator +"service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        String sqlMapperPath = File.separator + "mapper" + File.separator + className + "Mapper.xml";

        CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath, beanPath);
        CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath, mapperPath);
        CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", pckPath, servicePath);
        CommonPageParser.WriterPage(context, "ServiceTemplateImpl.ftl", pckPath, serviceImplPath);
        CommonPageParser.WriterPage(context, "MapperTemplate.xml", pckPath, sqlMapperPath);

    }



}
