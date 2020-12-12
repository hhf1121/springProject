package com.hhf.rocketMq;


import com.hhf.entity.User;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "testOrderTopic",consumerGroup = "testOrderConsumer",consumeMode = ConsumeMode.ORDERLY)//顺序消费
public class SpringOrderConsumer implements RocketMQListener<User> {


    @Override
    public void onMessage(User user) {
        System.out.println(user);
    }
}
