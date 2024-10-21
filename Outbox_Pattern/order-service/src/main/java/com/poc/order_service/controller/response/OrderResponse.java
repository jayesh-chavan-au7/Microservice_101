package com.poc.order_service.controller.response;

import lombok.Data;

import java.time.Instant;

@Data
public class OrderResponse {
    private String orderId;
    private Long orderAmount;
    private String merchantOrderReference;
    private String merchantId;
    private boolean praAuth;
    private Instant createdAt;
    private Instant updatedAt;
}
