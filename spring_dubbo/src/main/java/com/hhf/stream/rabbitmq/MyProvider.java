package com.hhf.stream.rabbitmq;


import com.hhf.dubbo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;


@Slf4j
@Component
@EnableBinding(value = {MyOutputInterface.class})
public class MyProvider {

    @Autowired
    private MyOutputInterface myOutputInterface;


    public void sendMsg(User user){
        String requestId = UUID.randomUUID().toString().replace("-", "");
        log.info("mq开始发消息，请求id：{}",requestId);
        StopWatch stopWatch=new StopWatch();
//        延时发送setHeader
        Message<User> message = MessageBuilder.withPayload(user).setHeader("x-delay", 1000 * 60 * 1)
                .build();
//        Message<User> message = MessageBuilder.withPayload(user).build();
        stopWatch.start("开始发消息");
        boolean send = myOutputInterface.sendMsg().send(message);
        stopWatch.stop();
        if(send){
            log.info("mq消息发送成功。请求id:{},总耗时：{}",requestId,stopWatch.getTotalTimeSeconds());
        }

    }


}