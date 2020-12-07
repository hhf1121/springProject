package com.hhf.rocketMq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/*** @author ：楼兰 *
 * @date ：Created in 2020/10/22 *
 * @description:
 */

@Component
public class SpringProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate; //发送普通消息的示例

    //普通消息
    public void sendMessage(String topic, Object msg) {
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }

    //发送事务消息的示例
    public void sendMessageInTransaction(String topic, String msg) throws InterruptedException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg).build();
            String destination = topic + ":" + tags[i % tags.length];
            SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);

        }
    }
}