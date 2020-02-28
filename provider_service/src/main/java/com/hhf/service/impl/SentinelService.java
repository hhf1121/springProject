package com.hhf.service.impl;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SentinelService {

    // 硬编码的方式
//    @PostConstruct
////    public void init() {
////        List<FlowRule> flowRules = new ArrayList<>();
////        /** * 定义 helloSentinelV3 受保护的资源的规则  */
////        //创建流控规则对象
////         FlowRule flowRule3 = new FlowRule();
////         //设置流控规则 QPS
////        flowRule3.setGrade(RuleConstant.FLOW_GRADE_QPS);
////        //设置受保护的资源
////        flowRule3.setResource("common");
////        //设置受保护的资源的阈值
////        flowRule3.setCount(1);
////        flowRules.add(flowRule3);
////        //加载配置好的规则
////        FlowRuleManager.loadRules(flowRules);
////    }

//    限流资源名common
//    @SentinelResource(value = "common",blockHandler ="blockHandlerCommon",fallbackClass = SentienlUtil.class)
    public Map<String,Object> getDate(){
        Map<String,Object> map=new HashMap<>();
        map.put("success",true);
        map.put("data",new Date());
        return  map;
    }

}
