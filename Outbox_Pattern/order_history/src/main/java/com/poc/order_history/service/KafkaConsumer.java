package com.poc.order_history.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.order_history.repository.OrderHistoryIndex;
import com.poc.order_history.service.envelopes.OrderEventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final OrderHistoryIndex orderHistoryIndex;

    @KafkaListener(topics = "events.public.order_outbox", groupId = "test")
    public void listenOrders(String message) {
        try {
            System.out.printf("message: %s %n", message);

            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode afterNode = jsonNode.path("payload").path("after");

            OrderEventEnvelope orderEventEnvelope = objectMapper.treeToValue(afterNode, OrderEventEnvelope.class);
            System.out.printf("Parsed After: %s %n", orderEventEnvelope);

            orderHistoryIndex.save(orderEventEnvelope);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
