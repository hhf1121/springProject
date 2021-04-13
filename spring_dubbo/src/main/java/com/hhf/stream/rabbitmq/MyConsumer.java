package com.hhf.stream.rabbitmq;


import com.hhf.dubbo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(MyInputInterface.class)
public class MyConsumer {


//    @StreamListener(MyInputInterface.MY_MESSAGE_INPUT)
    public void getMsg(@Payload Message<User> message){
        User dto = message.getPayload();
        log.info("开始消费my-spring-cloud-stream-rabbitmq消息...");
        //模拟异常，消费失败，就会保持到对应的死信队列
        int i=1/0;
        log.info(dto.getName()+"-"+dto.getAge());
    }

}
