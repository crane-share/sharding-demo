package com.crane.sharding.algorithm;

import com.crane.sharding.constant.ShardingConstant;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @author : spwei
 * create at:  2018/12/12  上午11:36
 * @description: 分库算法
 * 如果使用分库分表结合使用的话，不能简单进行shop_no 取模操作，需要加一个中间变量用来打散到不同的子表
 * 中间变量 = shardingValue.hashcode%（库数量*tableNum）
 * 库序号= 中间变量/tableNum
 */
public class DatabaseShardingAlgorithm_v310 implements PreciseShardingAlgorithm<String> {

    /**
     * 单个库的表数量
     */
    private int tableNum = 32;//默认是32个表

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        String databaseName = this.getDatabaseName(shardingValue);
        for (String each : availableTargetNames) {
            if (each.equals(databaseName)) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

        /**
     * 分库规则计算
     * 用分表规则计算分库规则（分表分库是同一字段情况下）
     *
     * @param shardingValue
     * @return
     */
    private String getDatabaseName(PreciseShardingValue<String> shardingValue) {
//        long suffix =  Long.parseLong(Math.abs(shardingValue.getValue().hashCode())) % ShardingConstant.SUB_DATABASE_COUNT;
//        long suffix =  Math.abs(shardingValue.getValue().hashCode()) % ShardingConstant.SUB_DATABASE_COUNT;
        //hash值对128取余
        int tableSuffix = Math.abs(shardingValue.getValue().hashCode()) % ShardingConstant.SUB_TABLE_COUNT;
        if (tableSuffix == 0) {
            tableSuffix = ShardingConstant.SUB_TABLE_COUNT;
        }
        //余数对64取余
        double tmp = tableSuffix / (double) ShardingConstant.SUB_SINGLE_TABLE_COUNT;

        //向上取整  128余数除64向上取整  结果是1或者是2
        int suffix = (int) Math.ceil(tmp);

        if (suffix > ShardingConstant.SUB_DATABASE_COUNT) {
            suffix = ShardingConstant.SUB_DATABASE_COUNT;
        }

        String ruleTable;
        ruleTable = ShardingConstant.PREFIX_DATASOURCE + suffix;
        return ruleTable;
    }
}