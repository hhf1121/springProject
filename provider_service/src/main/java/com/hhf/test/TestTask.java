package com.hhf.test;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 简单任务
 */

//@Component
@Slf4j
public class TestTask implements SimpleJob {

    List<Boolean> lists= Lists.newArrayList(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false);

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("分片:"+shardingContext.getShardingItem());
        log.info("----------执行----------"+ LocalDateTime.now());
        List<Boolean> result= Lists.newArrayList();
        for (Boolean list : lists) {
            log.info("源数据:"+lists);
            if(result.size()==lists.size()){
                break;
            }
            list=true;
            result.add(list);
            log.info("结果："+result);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
    }

}
