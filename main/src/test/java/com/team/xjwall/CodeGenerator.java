package com.team.xjwall;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
public class CodeGenerator {

    @Test
    public void test() {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // 代码生成的位置
        gc.setOutputDir("D:\\IdeaProjects\\xjwall\\main" + "/src/main/java");
        gc.setAuthor("XXX");
        gc.setOpen(false);  // 生成后是否打开资源管理器
        gc.setFileOverride(false); // 重新生成时文件是否覆盖

        //UserServie
        gc.setServiceName("%sService");    // 去掉 Service 接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR); // 主键策略
        gc.setDateType(DateType.ONLY_DATE); // 定义生成的实体类中日期类型
        gc.setSwagger2(true); // 开启 Swagger2 模式
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/xjwall?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("wang2003.3");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();

        // 包 com.demo.empms
        pc.setParent("com.team.xjwall");  // 设置根包
        pc.setController("controller");
        pc.setEntity("model");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("post","type","role","user","user_post","sensitive","role_permission_relation","operation","permission"); // 数据库中的表名,多个表名中间使用逗号隔开
        strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); // 生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter 链式操作
        strategy.setRestControllerStyle(true); // restful api 风格控制器
        strategy.setControllerMappingHyphenStyle(true); // url 中驼峰转连字符
        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }

}


