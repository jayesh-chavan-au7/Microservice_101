package com.poc.order_service.controller.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long orderAmount;
    private String merchantOrderReference;
    private String merchantId;
    private boolean praAuth;
}
