package com.crane.sharding.algorithm;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author : spwei
 * create at:  2018/12/12  上午11:40
 * @description: Between的分表算法实现
 */
public class TableRangeShardingAlgorithm_v310 implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Collection<String> collect = new ArrayList<>();
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        String tableIndex;
        for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            int temp=(i+"").hashCode()%(TablePreciseShardingAlgorithm_v310.dataNodeNum*collection.size());
            temp = Math.abs(temp);
            for (String each : collection) {
                tableIndex = each.substring(each.lastIndexOf("_")+ 1);
                if (tableIndex.equals(new DecimalFormat("000").format(temp%collection.size()))) {
                        collect.add(each);
                }
            }
        }
        return collect;
    }
}