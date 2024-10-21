package com.poc.order_service.controller;

import com.poc.order_service.controller.request.CreateOrderRequest;
import com.poc.order_service.controller.request.UpdateOrderAmountRequest;
import com.poc.order_service.controller.response.OrderResponse;
import com.poc.order_service.service.OrderServiceFacade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceFacade orderServiceFacade;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        return orderServiceFacade.getOrder(orderId)
                .map(orderDTO -> ResponseEntity.ok().body(orderDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse savedOrderResponse = orderServiceFacade.createOrder(createOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderResponse);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderAmount(
            @PathVariable String orderId,
            @RequestBody UpdateOrderAmountRequest updateOrderAmountRequest
    ) {
        OrderResponse updatedOrderResponse = orderServiceFacade.updateOrderAmount(orderId, updateOrderAmountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrderResponse);
    }
}