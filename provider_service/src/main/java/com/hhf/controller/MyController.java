package com.hhf.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * sentinel、限流+降级
 */
@RestController
public class MyController {

    @PostConstruct
    public void init() {
        List<FlowRule> flowRules = new ArrayList<>();
        /** 7 * 定义 helloSentinelV2 受保护的资源的规则 8 */
        //创建流控规则对象 10
        FlowRule flowRule2 = new FlowRule();  //设置流控规则 QPS
        flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置受保护的资源
        flowRule2.setResource("helloSentinelV2");
        //设置受保护的资源的阈值
        flowRule2.setCount(1);
        flowRules.add(flowRule2);
        //加载配置好的规则
        FlowRuleManager.loadRules(flowRules);
    }


    @SentinelResource(value = "helloSentinelV2",blockHandler ="testHelloSentinelV2BlockMethod")
    @RequestMapping("testSentinel")
    public String testSentinel(){
        return "success";
    }

    public String testHelloSentinelV2BlockMethod(BlockException e){
        return "testHelloSentinelV2BlockMethod";
    }

}
