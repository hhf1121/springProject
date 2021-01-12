package com.hhf.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
public class KafKaConsumer {

    @KafkaListener(topics = "mytopic",groupId = "testGroup")
    public void listen(ConsumerRecord<String,String> record){
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
    }

}
