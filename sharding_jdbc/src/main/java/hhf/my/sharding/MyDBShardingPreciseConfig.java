package hhf.my.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * sharding、库分片策略：Precise
 * 自定义
 */

public class MyDBShardingPreciseConfig implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        //sharding$->{1..2}
        String logicTableName = preciseShardingValue.getLogicTableName();
        Long idValue = preciseShardingValue.getValue();
        String idName = preciseShardingValue.getColumnName();
        // id%2+1
        BigInteger res = BigInteger.valueOf(idValue).mod(new BigInteger("2")).add(new BigInteger("1"));
        // sharding1/2
        String key="sharding"+res;
        //collection逻辑库名
        if(collection.contains(key)){
            return key;
        }
        throw new UnsupportedOperationException("不支持"+key+"请检查配置");
    }
}
