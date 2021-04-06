package com.hhf.stream.rabbitmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface MyInputInterface {

    final String MY_MESSAGE_INPUT = "my_message_input";

    @Input(MY_MESSAGE_INPUT)
    SubscribableChannel reciverMsg();


}
