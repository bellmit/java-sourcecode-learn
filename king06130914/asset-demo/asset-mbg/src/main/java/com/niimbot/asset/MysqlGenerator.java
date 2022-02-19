package com.niimbot.asset;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class MysqlGenerator {
    private static final String MODULE = "/asset-mbg";
    // 生成代码存放路径
    private static final String MODULE_PATH = MODULE + "/src/main/java";
    // 生成mapper存放路径
    private static final String MODULE_MAPPER_PATH = MODULE + "/src/main/resources/mapper";
    // 创建者
    private static final String AUTHOR = "dk";
    // 是否开启swagger
    private static final Boolean ENABLE_SWAGGER = true;

    // 数据库配置
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL="jdbc:mysql://127.0.0.1:3306/asset_dev_java?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    /**
     * 表名，多个英文逗号分割
     */
    private static final String tableNames =
            "as_pm_version,as_industry,as_contract_expand,as_contract,as_organization"
            ;

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(setGlobalConfig());
        // 数据源配置
        mpg.setDataSource(setDataSource());
        // 包配置
        mpg.setPackageInfo(setPackageInfo());
        // 自定义配置
        mpg.setCfg(setCfg());
        // 配置模板
        mpg.setTemplate(setTemplate());
        // 策略配置
        mpg.setStrategy(setStrategy());
        //配置模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        //生成
        mpg.execute();
    }

    /**
     * 数据库表策略配置
     */
    private static StrategyConfig setStrategy() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        //表名，多个英文逗号分割
        strategy.setInclude(tableNames.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        return strategy;
    }

    /**
     * 配置模板
     */
    private static TemplateConfig setTemplate() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setController(null);
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig setCfg() {
        //项目路径
        String projectPath = System.getProperty("user.dir");
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + MODULE_MAPPER_PATH + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 包配置
     */
    private static PackageConfig setPackageInfo() {
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.niimbot.asset");
        pc.setEntity("model");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        return pc;
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig setDataSource() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(CONNECTION_URL);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setTypeConvert(new MySqlTypeConvertCustom());
        return dsc;
    }

    /**
     * 全局配置
     */
    private static GlobalConfig setGlobalConfig() {
        //项目路径
        String projectPath = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + MODULE_PATH);
        gc.setAuthor(AUTHOR);
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setServiceName("%sService");
        gc.setOpen(false);
        gc.setSwagger2(ENABLE_SWAGGER);
        gc.setDateType(DateType.TIME_PACK);
        //主键策略配置
        gc.setIdType(IdType.ASSIGN_ID);
        return gc;
    }
}

/**
 * 自定义类型转换
 */
class MySqlTypeConvertCustom extends MySqlTypeConvert implements ITypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("smallint(1)")) {
            return DbColumnType.SHORT;
        }
        return super.processTypeConvert(globalConfig, fieldType);
    }
}

