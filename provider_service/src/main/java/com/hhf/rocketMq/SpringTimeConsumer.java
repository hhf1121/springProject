package com.hhf.rocketMq;


import com.hhf.entity.User;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 延时消费
 */
@Component
@RocketMQMessageListener(topic = "testTimeTopic",consumerGroup = "testTimeConsumer",consumeMode = ConsumeMode.ORDERLY)//顺序消费
public class SpringTimeConsumer implements RocketMQListener<User> {


    @Override
    public void onMessage(User user) {
        System.out.println("consumerTime:"+ LocalTime.now());
        System.out.println(user);
    }
}
