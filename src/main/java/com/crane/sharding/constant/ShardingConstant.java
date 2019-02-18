package com.crane.sharding.constant;

public class ShardingConstant {

    /**
     * 数据库名，在ShardingMybatisConfig中使用
     */
    public static final String PREFIX_DATASOURCE = "ds";

    /**
     * 分库数量,1
     */
    public static final Integer SUB_DATABASE_COUNT = 2;


    /**
     * 数据库表名，在ShardingMybatisConfig中使用
     */
    public static final String PREFIX_TRACE_TABLE_NAME = "t_sco_truck_trace_";

    /**
     * 分库规则，在ShardingMybatisConfig中使用
     */
    public static final String SHARDING_RULE = "create_time";

    /**
     * 总分表数量
     */
    public static final int SUB_TABLE_COUNT = 12;

    /**
     * 单库里所含有的表数量，也可以通过SUB_TABLE_COUNT/SUB_DATABASE_COUNT获取
     */
    public static final int SUB_SINGLE_TABLE_COUNT = 16;

    /**
     * 一个0字符串
     */
    public static final String ONE_ZERO_STRING = "0";

    /**
     * 两个0字符串
     */
    public static final String TWO_ZERO_STRING = "00";

}
