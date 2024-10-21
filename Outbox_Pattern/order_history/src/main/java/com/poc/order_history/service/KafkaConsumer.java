package com.poc.order_history.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "events.public.order_outbox", groupId = "test")
    public void listenOrders(String message) {
        System.out.printf("message: %s %n", message);
    }
}
