package com.hhf.stream.rabbitmq;


import com.hhf.dubbo.pojo.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableBinding(MyInputInterface.class)
public class MyConsumer {


//    @StreamListener(MyInputInterface.MY_MESSAGE_INPUT)
//    public void getMsg(@Payload Message<User> message){
//        User dto = message.getPayload();
//        log.info("开始消费my-spring-cloud-stream-rabbitmq消息...");
//        //模拟异常，消费失败，就会保持到对应的死信队列
//        int i=1/0;
//        log.info(dto.getName()+"-"+dto.getAge());
//    }


    /**
     * 手动ack，配合acknowledge-mode: manual 使用
     * @param message
     * @param channel
     * @param deliveryTag
     * @throws IOException
     */
//    @StreamListener(MyInputInterface.MY_MESSAGE_INPUT)
    public void getMsg(@Payload Message<User> message,
                       @Header(AmqpHeaders.CHANNEL) Channel channel,
                       @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        User dto = message.getPayload();
        log.info("开始消费my-spring-cloud-stream-rabbitmq消息...");
        //模拟异常，消费失败，就会保持到对应的死信队列
//        int i=1/0;
        log.info(dto.getName()+"-"+dto.getAge());
        channel.basicAck(deliveryTag,false);//false,此条。true,小于deliveryTag的，全部ack
    }


}
