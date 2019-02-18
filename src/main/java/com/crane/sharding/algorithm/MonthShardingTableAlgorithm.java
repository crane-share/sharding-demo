package com.crane.sharding.algorithm;

import com.crane.sharding.constant.ShardingConstant;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * 分表规则 按月份分成12张表 1.5.4版本
 */
@Slf4j
public class MonthShardingTableAlgorithm{
//public class MonthShardingTableAlgorithm implements SingleKeyTableShardingAlgorithm<Date> {
//
//    @Override
//    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Date> shardingValue) {
//        String ruleTable = this.getTableName(shardingValue);
//        for (String each : availableTargetNames) {
//            if (each.equals(ruleTable)) {
//                return each;
//            }
//        }
//        log.error("there is no doEqualSharding database,shardingValue:{},tableName:{}", shardingValue, ruleTable);
//        throw new IllegalArgumentException();
//    }
//
//    @Override
//    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Date> shardingValue) {
//        String ruleTable = this.getTableName(shardingValue);
//        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
//        for (String tableName : availableTargetNames) {
//            if (tableName.equals(ruleTable)) {
//                result.add(tableName);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Date> shardingValue) {
//        String ruleTable = this.getTableName(shardingValue);
//        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
//        for (String each : availableTargetNames) {
//            if (each.equals(ruleTable)) {
//                result.add(each);
//            }
//        }
//        return result;
//    }
//
//    private String getTableName(ShardingValue<Date> shardingValue) {
//        Date date = shardingValue.getValue();
//        Instant instant = date.toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
//        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
//        int suffix = localDate.getMonth().getValue();
//        String ruleTable;
//        if (suffix < 10) {
//            ruleTable = ShardingConstant.PREFIX_TRACE_TABLE_NAME + ShardingConstant.TWO_ZERO_STRING + suffix;
//        } else {
//            ruleTable = ShardingConstant.PREFIX_TRACE_TABLE_NAME + ShardingConstant.ONE_ZERO_STRING + suffix;
//        }
//        log.info("**************[QUERYTABLE] tableName is " + ruleTable + ", createTime is " + shardingValue.getValue() + "**************");
//        return ruleTable;
//    }

}
