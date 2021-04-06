package com.hhf.config.executor;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;


@Slf4j
@Component
public class MyTask {

    @XxlJob("myTask")
    public ReturnT<String> myTask(String param){
       log.info("myTask:start..."+param);
        return SUCCESS;
    }

}
