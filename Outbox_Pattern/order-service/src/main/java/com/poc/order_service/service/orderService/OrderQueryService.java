package com.poc.order_service.service.orderService;

import com.poc.order_service.controller.response.OrderResponse;
import com.poc.order_service.model.Orders;
import com.poc.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public Optional<OrderResponse> getOrder(String orderId) {
        Optional<Orders> order = orderRepository.findByOrderId(orderId);
        return order.map(Orders::toResponse);
    }
}
