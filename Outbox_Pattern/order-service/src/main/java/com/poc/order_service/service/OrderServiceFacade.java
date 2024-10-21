package com.poc.order_service.service;

import com.poc.order_service.controller.request.CreateOrderRequest;
import com.poc.order_service.controller.request.UpdateOrderAmountRequest;
import com.poc.order_service.controller.response.OrderResponse;
import com.poc.order_service.service.orderService.OrderCreationService;
import com.poc.order_service.service.orderService.OrderEditingService;
import com.poc.order_service.service.orderService.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceFacade {

    private final OrderCreationService orderCreationService;
    private final OrderQueryService orderQueryService;
    private final OrderEditingService orderEditingService;

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        return orderCreationService.createOrder(createOrderRequest);
    }

    public Optional<OrderResponse> getOrder(String orderId) { return orderQueryService.getOrder(orderId); }

    public OrderResponse updateOrderAmount(String orderId, UpdateOrderAmountRequest updateOrderAmountRequest) {
        return orderEditingService.updateOrderAmount(orderId, updateOrderAmountRequest);
    }
}
