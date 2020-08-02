package com.hhf.config;


import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.hhf.test.TestTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ElasticJobConfig {

    @Autowired
    private TestTask testTask;

    @Autowired
    private CoordinatorRegistryCenter coordinatorRegistryCenter;

    private LiteJobConfiguration createLiteJobConfiguration(Class<? extends SimpleJob> jobClass,
                                                            String cron,
                                                            int shardingTotalCount,
                                                            String params){
        //创建
        JobCoreConfiguration.Builder builder = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount);
        //设置params
        if(!StringUtils.isEmpty(params)){
            builder.shardingItemParameters(params);
        }
        //创建SimpleJobConfiguration
        SimpleJobConfiguration simpleJobConfiguration=new SimpleJobConfiguration(builder.build(),jobClass.getCanonicalName());
        //创建 LiteJobConfiguration
        LiteJobConfiguration build = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
        return  build;
    }



    @Bean(initMethod = "init")
    public SpringJobScheduler initSimpleElasticJob(){
        //创建SpringJobScheduler
        SpringJobScheduler springJobScheduler = new SpringJobScheduler(testTask,
                coordinatorRegistryCenter,
                createLiteJobConfiguration(testTask.getClass(), "0/3 * * * * ?", 1, ""));
        return springJobScheduler;
    }


}
