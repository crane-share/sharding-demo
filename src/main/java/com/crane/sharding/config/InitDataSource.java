package com.crane.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

public class InitDataSource {

    public  static DataSource initDataSource(String url, String username, String password){

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        dataSource.setValidationQuery("select 1 from dual");
        dataSource.setMaxWait(100000);
        return dataSource;

    }
}
