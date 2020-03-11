package com.hhf.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockUtils {

    public static String testHelloSentinelV2BlockMethod(BlockException e){
        return "testHelloSentinelV2BlockMethod";
    }
}
