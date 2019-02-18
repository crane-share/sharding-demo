package com.crane.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.crane.sharding.algorithm.DatabaseShardingAlgorithm_v310;
import com.crane.sharding.algorithm.TablePreciseShardingAlgorithm_v310;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
* @Description: TODO  sharding-jdbc 3.1.0版本
* @Title: ShardingMybatisConfig_v310
* @author syhe
* @date 2019-02-15 10:14
*/
@Configuration
@Slf4j
public class ShardingMybatisConfig_v310 implements EnvironmentAware {

    private Environment environment;
    /**
     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean
    public DataSource ds0(){
        String dataUrl = environment.getProperty("spring.datasource-01.url");
        String username = environment.getProperty("spring.datasource-01.username");
        String password = environment.getProperty("spring.datasource-01.password");
        return InitDataSource.initDataSource(dataUrl, username, password);
    }

    /**
     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean
    public DataSource ds1(){
        String dataUrl = environment.getProperty("spring.datasource-02.url");
        String username = environment.getProperty("spring.datasource-02.username");
        String password = environment.getProperty("spring.datasource-02.password");
        return InitDataSource.initDataSource(dataUrl, username, password);
    }


    /**
     * 创建sharding-jdbc的数据源DataSource，MybatisAutoConfiguration会使用此数据源
     *
     * @param
     * @return
     * @throws SQLException
     */
    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource(@Qualifier("ds0") DataSource ds0,@Qualifier("ds1") DataSource ds1) throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration(); //sharding配置入口

        shardingRuleConfig.getTableRuleConfigs().add(getCommonTableRuleConfiguration("t_sco_truck_trace"));

        // 绑定组，关联查询同一组的表 只需要定位一个表 其他表不需再次定位
        shardingRuleConfig.getBindingTableGroups().add("t_sco_truck_trace");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time", new DatabaseShardingAlgorithm_v310()));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time", new TablePreciseShardingAlgorithm_v310()));
        // 默认id生成算法 雪花算法
        shardingRuleConfig.setDefaultKeyGenerator(new DefaultKeyGenerator());
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", ds0);
        dataSourceMap.put("ds1", ds1);
        Properties properties = new Properties();
        // 显示分库分表后的sql
        properties.setProperty("sql.show", "true");
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new HashMap<>(), properties);


    }

    /**
     * @Description:TODO 根据表名配置规则
     * @param tableName
     * @return: io.shardingsphere.api.config.rule.TableRuleConfiguration
     * @throws:
     * @author: syhe
     * @date: 2019-02-15 14:17
     */
    private  TableRuleConfiguration getCommonTableRuleConfiguration(String tableName){//逻辑表名
        return getCommonTableRuleConfiguration(tableName,true);
    }

    /**
     * @Description:TODO 根据表名配置分表规则
     * @param tableName 逻辑表名
     * @param keyGener 是否生成主键
     * @return: io.shardingsphere.api.config.rule.TableRuleConfiguration
     * @throws:
     * @author: syhe
     * @date: 2019-02-15 14:17
     */
    private  TableRuleConfiguration getCommonTableRuleConfiguration(String tableName,Boolean keyGener){
        /**
         * 测试和本地环境默认2个表
         * 其他环境默认32张表
         * 方便开发
         */
        return getCommonTableRuleConfiguration(tableName,keyGener,3);
    }

    /**
     * @Description:TODO 根据表名生成分表规则
     * @param tableName 逻辑表名
     * @param keyGener 是否生成主键
     * @param tableNum 分表数量
     * @return: io.shardingsphere.api.config.rule.TableRuleConfiguration
     * @throws:
     * @author: syhe
     * @date: 2019-02-15 14:18
     */
    private  TableRuleConfiguration getCommonTableRuleConfiguration(String tableName,Boolean keyGener,int tableNum){
        TableRuleConfiguration result = new TableRuleConfiguration();//分表规则
        result.setLogicTable(tableName);//设置逻辑表名成
        StringBuilder builder = new StringBuilder();
        DecimalFormat format  = new DecimalFormat("000");
        for (int i=0;i<tableNum;i++){
            builder.append(",ds0."+tableName+"_"+format.format(i)).append(",ds1."+tableName+"_"+format.format(i));
        }
        result.setActualDataNodes(builder.toString().substring(1));//设置实际的表名
        // 分表默认分32个表，如果有自定义表数量则需要重新定义分库分表规则
        if(tableNum != 32){
            DatabaseShardingAlgorithm_v310 databaseShardingAlgorithm =  new DatabaseShardingAlgorithm_v310();
            databaseShardingAlgorithm.setTableNum(tableNum);//设置表数量
            //根据字段设定分表原则
            StandardShardingStrategyConfiguration dataConfiguration =  new StandardShardingStrategyConfiguration("create_time", databaseShardingAlgorithm);
            result.setDatabaseShardingStrategyConfig(dataConfiguration);
        }
        // 需要自动生成的列 默认生成算法DefaultKeyGenerator()，如果选择了id自动生成，则mapper中的insert语句中不要包含id列。
        if(keyGener){
            result.setKeyGeneratorColumnName("id");
        }
        return result;
    }

    /**
     * 需要手动配置事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManagerSub(@Qualifier("shardingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "subSqlSessionFactory")
    @Primary
    public SqlSessionFactory subSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置mybatis的主配置文件
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource mybatisXml = resourcePatternResolver.getResource("classpath:mybatis/mybatis-config.xml");
        bean.setConfigLocation(mybatisXml);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/sub/*.xml"));
        //设置别名包
        bean.setTypeAliasesPackage("com.crane.sharding.DO");
        return bean.getObject();
    }

    @Bean(name = "subSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("subSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean(name = "shardingMapperScannerConfigurer")
    public MapperScannerConfigurer shardingMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("subSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.crane.sharding.dao");
        return mapperScannerConfigurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
