package com.crane.sharding.config;

import com.crane.sharding.algorithm.MonthShardingTableAlgorithm;
import com.crane.sharding.constant.ShardingConstant;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
* @Description: TODO sharding-jdbc 1.5.4版本
* @Title: ShardingMybatisConfig_v154
* @author syhe
* @date 2019-02-15 10:11
*/
@Configuration
public class ShardingMybatisConfig_v154 implements EnvironmentAware {

    private Environment environment;

//    /**
//     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
//     *
//     * @return
//     */
//    @Bean(name = "dataSource1")
//    @ConfigurationProperties(prefix = "spring.datasource.test1")
//    public DataSource dataSource1() {
//        String url = environment.getProperty("spring.datasource.url");
//        String username = environment.getProperty("spring.datasource.username");
//        String password = environment.getProperty("spring.datasource.password");
//        return InitDataSource.initDataSource(url, username, password);
//    }
//
//    /**
//     * 配置数据源规则，即将多个数据源交给sharding-jdbc管理，并且可以设置默认的数据源，
//     * 当表没有配置分库规则时会使用默认的数据源
//     *
//     * @param
//     * @param
//     * @return
//     */
//    @Bean
//    public DataSourceRule dataSourceRule(@Qualifier("dataSource1") DataSource dataSource1) {
//        //设置分库映射
//        Map<String, DataSource> dataSourceMap = new HashMap<>(1);
//        dataSourceMap.put(ShardingConstant.PREFIX_DATASOURCE + "01", dataSource1);
//        //设置默认库，两个库以上时必须设置默认库。默认库的数据源名称必须是dataSourceMap的key之一
//        return new DataSourceRule(dataSourceMap, "dataSource01");
//    }
//
//    /**
//     * 配置数据源策略和表策略，具体策略需要自己实现
//     *
//     * @param dataSourceRule
//     * @return
//     */
//    @Bean
//    public ShardingRule shardingRule(DataSourceRule dataSourceRule) {
//        List<String> traceTableList = Lists.newArrayList();
//        for (int i = 0; i < ShardingConstant.SUB_TABLE_COUNT; i++) {
//            if (i < 9) {
//                traceTableList.add(ShardingConstant.PREFIX_TRACE_TABLE_NAME + ShardingConstant.TWO_ZERO_STRING + (i + 1));
//                continue;
//            } else if (i < 99) {
//                traceTableList.add(ShardingConstant.PREFIX_TRACE_TABLE_NAME + ShardingConstant.ONE_ZERO_STRING + (i + 1));
//                continue;
//            } else {
//                traceTableList.add(ShardingConstant.PREFIX_TRACE_TABLE_NAME + (i + 1));
//            }
//        }
//
//        //具体分库分表策略
//        TableRule traceTableRule =
//                TableRule.builder(ShardingConstant.PREFIX_TRACE_TABLE_NAME.substring(0, ShardingConstant.PREFIX_TRACE_TABLE_NAME.length() - 1))
//                .actualTables(traceTableList)
//                .tableShardingStrategy(new TableShardingStrategy(ShardingConstant.SHARDING_RULE, new MonthShardingTableAlgorithm()))
//                .dataSourceRule(dataSourceRule)
//                .build();
//
//        //绑定表策略，在查询时会使用主表策略计算路由的数据源，因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
//        List<BindingTableRule> bindingTableRules = new ArrayList<>();
//        bindingTableRules.add(new BindingTableRule(Collections.singletonList(traceTableRule)));
//        return ShardingRule.builder()
//                .dataSourceRule(dataSourceRule)
//                .tableRules(Collections.singletonList(traceTableRule))
//                .bindingTableRules(bindingTableRules)
//                .tableShardingStrategy(new TableShardingStrategy(ShardingConstant.SHARDING_RULE, new MonthShardingTableAlgorithm()))
//                .build();
//    }
//
//    /**
//     * 创建sharding-jdbc的数据源DataSource，MybatisAutoConfiguration会使用此数据源
//     *
//     * @param shardingRule
//     * @return
//     * @throws SQLException
//     */
//    @Bean(name = "shardingDataSource")
//    public DataSource shardingDataSource(ShardingRule shardingRule) throws SQLException {
//        return ShardingDataSourceFactory.createDataSource(shardingRule);
//    }
//
//    /**
//     * 需要手动配置事务管理器
//     *
//     * @param dataSource
//     * @return
//     */
//    @Bean(name = "shadringTransactionManager")
//    public DataSourceTransactionManager transactionManager(@Qualifier("shardingDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "subSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory subSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/sub/*.xml"));
//        //设置别名包
//        bean.setTypeAliasesPackage("com.crane.sharding.DO");
//        return bean.getObject();
//    }
//
//    @Bean(name = "subSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("subSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean(name = "shardingMapperScannerConfigurer")
//    public MapperScannerConfigurer shardingMapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("subSqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.crane.sharding.dao");
//        return mapperScannerConfigurer;
//    }
//
//    @Bean
//    public SntKeyGenerator sntKeyGenerator() {
//        return new SntKeyGenerator();
//    }
//
    //@Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
