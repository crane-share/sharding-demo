package com.crane.sharding.algorithm;

import com.crane.sharding.constant.ShardingConstant;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

/**
 * @author : spwei
 * create at:  2018/12/12  上午11:39
 * @description: 分表算法：     ==和IN的分表算法实现
 *
 * 中间变量=shardingKey %（库数量*单个库的表数量）
 * 表序号=中间变量%单个库的表数量
 */
@Slf4j
public class TablePreciseShardingAlgorithm_v310 implements PreciseShardingAlgorithm<String> {

    /**
     * 数据库数量
     * 可定制
     */
    protected static  final int dataNodeNum = 2;

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        String ruleTable = this.getTableName(shardingValue);
        for (String each : availableTargetNames) {
            if (each.equals(ruleTable)) {
                return each;
            }
        }
        log.error("there is no doEqualSharding database,shardingValue:{},tableName:{}", shardingValue, ruleTable);
        throw new IllegalArgumentException();
    }

    private String getTableName(PreciseShardingValue<String> shardingValue) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date date = Date.from(LocalDateTime.parse(shardingValue.getValue(), dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant());
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        int suffix = localDate.getMonth().getValue();
        String ruleTable;
        if (suffix < 10) {
            ruleTable = ShardingConstant.PREFIX_TRACE_TABLE_NAME + ShardingConstant.TWO_ZERO_STRING + suffix;
        } else {
            ruleTable = ShardingConstant.PREFIX_TRACE_TABLE_NAME + ShardingConstant.ONE_ZERO_STRING + suffix;
        }
        System.out.println("================================================>>>>>>"+ ruleTable);
        log.info("**************[QUERYTABLE] tableName is " + ruleTable + ", createTime is " + shardingValue.getValue() + "**************");
        return ruleTable;
    }
}