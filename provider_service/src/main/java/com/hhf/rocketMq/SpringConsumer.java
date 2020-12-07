package com.hhf.rocketMq;

import com.hhf.entity.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/*** @author ：楼兰
 * @date ：Created in 2020/10/22
 * @description:
 **/
@Component
@RocketMQMessageListener(consumerGroup = "MyConsumerGroup", topic = "TestTopic")
public class SpringConsumer implements RocketMQListener<User> {
    @Override
    public void onMessage(User message) {
        System.out.println("Received message : " + message);
    }
}
