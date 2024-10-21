package com.poc.order_service.service.orderService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.order_service.controller.request.CreateOrderRequest;
import com.poc.order_service.controller.response.OrderResponse;
import com.poc.order_service.model.OrderOutbox;
import com.poc.order_service.model.Orders;
import com.poc.order_service.repository.OrderOutboxRepository;
import com.poc.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderCreationService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Orders savedOrder = saveOrder(createOrderRequest);
        createOutboxForCreateEvent(savedOrder);
        return Orders.toResponse(savedOrder);
    }

    @NotNull
    private Orders saveOrder(CreateOrderRequest createOrderRequest) {
        try {
            Orders order = Orders.fromRequest(createOrderRequest);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error saving order", e);
        }
    }

    private void createOutboxForCreateEvent(Orders savedOrder) {
        try {
            String eventPayload = objectMapper.writeValueAsString(savedOrder);
            OrderOutbox outbox = OrderOutbox.createOrderOutbox(savedOrder.getOrderId(), eventPayload);
            orderOutboxRepository.save(outbox);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing order to JSON", e);
        } catch (Exception e) {
            throw new RuntimeException("Error saving outbox entry", e);
        }
    }
}
