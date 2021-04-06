package com.hhf.stream.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface MyOutputInterface {

    final String MY_MESSAGE_OUTPUT = "my_message_input1";

    @Output(MY_MESSAGE_OUTPUT)
    MessageChannel sendMsg();

}
