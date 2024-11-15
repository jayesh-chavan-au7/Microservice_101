package com.poc.order_service.service.orderService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.order_service.controller.request.UpdateOrderAmountRequest;
import com.poc.order_service.controller.response.OrderResponse;
import com.poc.order_service.exceptions.OrderNotFoundException;
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
public class OrderEditingService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository orderOutboxRepository;

    @Transactional
    public OrderResponse updateOrderAmount(String orderId, UpdateOrderAmountRequest updateOrderAmountRequest) {
        Orders updatedOrder = updateOrder(orderId, updateOrderAmountRequest);
        createOutboxForUpdateEvent(updatedOrder);
        return Orders.toResponse(updatedOrder);
    }

    @NotNull
    private Orders updateOrder(String orderId, UpdateOrderAmountRequest updateOrderAmountRequest) {
        try {
            Orders order = orderRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
            order.setOrderAmount(updateOrderAmountRequest.getOrderAmount());
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error updating order", e);
        }
    }

    private void createOutboxForUpdateEvent(Orders updatedOrder) {
        try {
            OrderOutbox outbox = OrderOutbox.updateOrderOutbox(
                    updatedOrder.getOrderId(),
                    updatedOrder.getOrderAmount(),
                    updatedOrder.getMerchantId(),
                    updatedOrder.getMerchantOrderReference()
            );
            orderOutboxRepository.save(outbox);
        } catch (Exception e) {
            throw new RuntimeException("Error saving outbox entry for updated order", e);
        }
    }
}

