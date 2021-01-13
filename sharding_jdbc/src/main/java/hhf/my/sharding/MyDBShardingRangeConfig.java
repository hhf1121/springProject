package hhf.my.sharding;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * sharding、库分片策略：range
 * 自定义
 */

public class MyDBShardingRangeConfig implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        //范围
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        Long upperEndpoint = valueRange.upperEndpoint();//高点
        Long lowerEndpoint = valueRange.lowerEndpoint();//低点
        String logicTableName = rangeShardingValue.getLogicTableName();
        return Lists.newArrayList("sharding1","sharding2");
    }
}
