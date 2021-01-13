package hhf.my.sharding;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Complex、表分片策略
 * 自定义
 */

public class MyTableShardingComplexConfig implements ComplexKeysShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> value) {
        Range<Long> id = value.getColumnNameAndRangeValuesMap().get("id");
        Collection<Long> yes = value.getColumnNameAndShardingValuesMap().get("yes");
        List<String> result= Lists.newArrayList();
        for (Long aLong : yes) {
            BigInteger bigInteger = BigInteger.valueOf(aLong);
            BigInteger target = bigInteger.mod(new BigInteger("2")).add(new BigInteger("1"));
            result.add(value.getLogicTableName()+"_"+target);
        }
        return result;
    }

}
