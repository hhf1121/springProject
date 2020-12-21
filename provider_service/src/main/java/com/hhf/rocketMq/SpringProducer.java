package com.hhf.rocketMq;

import com.hhf.entity.User;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.time.LocalTime;

/*** @author ：楼兰 *
 * @date ：Created in 2020/10/22 *
 * @description:
 */

@Component
public class SpringProducer {

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate; //发送普通消息的示例

    //普通消息发送示例
    public void sendMessage(String topic, Object msg) {
        //1.异步发送
        this.rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("发送失败");
            }
        });
        //2.同步发送
        SendResult sendResult = this.rocketMQTemplate.syncSend(topic, msg);
        if(sendResult.getSendStatus().name().equals("SEND_OK")){
            System.out.println("发送成功！");
        }
        //3.单向发送
        this.rocketMQTemplate.sendOneWay(topic,msg);
    }

    //发送事务消息的示例
    public void sendMessageInTransaction(String topic, User msg){//testTransactionTopic
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            Message<User> message = MessageBuilder.withPayload(msg).build();
            String destination = topic + ":" + tags[i % tags.length];
            SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
            if(sendResult.getSendStatus().name().equals("SEND_OK")){
                System.out.println("发送成功");
            }
        }
    }

    //发送顺序消息的示例
    public void sendMessageInSort(String topic,User user){
        rocketMQTemplate.syncSendOrderly(topic, user, String.valueOf(user.getId()));
    }

    //发送延时消息的示例
    public void sendMessageInTime(String topic,User user,Integer delayLevel){
        //messageDelayLevel	1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        //String destination, Message<?> message, long timeout, int delayLevel
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(user).build(), 2000,delayLevel);
        System.out.println("sendTime:"+ LocalTime.now());
    }
}