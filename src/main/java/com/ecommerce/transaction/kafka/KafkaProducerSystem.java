package com.ecommerce.transaction.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerSystem {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    public void send(String topic, Object message) {
        this.kafkaTemplate.send(topic, message);
    }
}
