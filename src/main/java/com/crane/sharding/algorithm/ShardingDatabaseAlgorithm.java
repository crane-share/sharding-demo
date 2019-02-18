package com.crane.sharding.algorithm;

import com.crane.sharding.constant.ShardingConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 分库规则 1.5.4版本
 */
public class ShardingDatabaseAlgorithm  {
//public class ShardingDatabaseAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {
//
//    private static Logger LOGGER = LoggerFactory.getLogger(ShardingDatabaseAlgorithm.class);
//
//
//    @Override
//    public String doEqualSharding(Collection<String> databaseNames, ShardingValue<String> shardingValue) {
//        String databaseName = this.getDatabaseName(shardingValue);
//        for (String each : databaseNames) {
//            if (each.equals(databaseName)) {
//                return each;
//            }
//        }
//        LOGGER.error("there is no doEqualSharding database,shardingValue:{},dataBaseName:{}", shardingValue, databaseName);
//        throw new IllegalArgumentException();
//    }
//
//    @Override
//    public Collection<String> doInSharding(Collection<String> databaseNames, ShardingValue<String> shardingValue) {
//        String databaseName = this.getDatabaseName(shardingValue);
//        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
//        for (String value : shardingValue.getValues()) {
//            for (String tableName : databaseNames) {
//                if (tableName.equals(databaseName)) {
//                    result.add(tableName);
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Collection<String> doBetweenSharding(Collection<String> databaseNames, ShardingValue<String> shardingValue) {
//        String databaseName = this.getDatabaseName(shardingValue);
//
//        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
////        Range<Long> range = (Range<Long>) shardingValue.getValueRange();
////        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
//        for (String each : databaseNames) {
//            if (each.equals(databaseName)) {
//                result.add(each);
//            }
//        }
////        }
//        return result;
//    }
//
//    /**
//     * 分库规则计算
//     * 用分表规则计算分库规则（分表分库是同一字段情况下）
//     *
//     * @param shardingValue
//     * @return
//     */
//    private String getDatabaseName(ShardingValue<String> shardingValue) {
////        long suffix =  Long.parseLong(Math.abs(shardingValue.getValue().hashCode())) % ShardingConstant.SUB_DATABASE_COUNT;
////        long suffix =  Math.abs(shardingValue.getValue().hashCode()) % ShardingConstant.SUB_DATABASE_COUNT;
//        //hash值对128取余
//        int tableSuffix = Math.abs(shardingValue.getValue().hashCode()) % ShardingConstant.SUB_TABLE_COUNT;
//        if (tableSuffix == 0) {
//            tableSuffix = ShardingConstant.SUB_TABLE_COUNT;
//        }
//        //余数对64取余
//        double tmp = tableSuffix / (double) ShardingConstant.SUB_SINGLE_TABLE_COUNT;
//
//        //向上取整  128余数除64向上取整  结果是1或者是2
//        int suffix = (int) Math.ceil(tmp);
//
//        if (suffix > ShardingConstant.SUB_DATABASE_COUNT) {
//            suffix = ShardingConstant.SUB_DATABASE_COUNT;
//        }
//
//        String ruleTable;
//        if (suffix < 10) {
//            ruleTable = ShardingConstant.PREFIX_DATASOURCE + ShardingConstant.ONE_ZERO_STRING + suffix;
//        } else {
//            ruleTable = ShardingConstant.PREFIX_DATASOURCE + suffix;
//        }
//        return ruleTable;
//    }

}
