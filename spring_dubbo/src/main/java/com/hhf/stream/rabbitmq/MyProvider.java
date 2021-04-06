package com.hhf.stream.rabbitmq;


import com.hhf.dubbo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@EnableBinding(value = {MyOutputInterface.class})
public class MyProvider {

    @Autowired
    private MyOutputInterface myOutputInterface;


    public void sendMsg(User user){
        Message<User> message = MessageBuilder.withPayload(user).build();
        boolean send = myOutputInterface.sendMsg().send(message);
        if(send){
            log.info("发送消息成功");
        }
    }


}