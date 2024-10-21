package com.poc.order_service.model;

import com.poc.order_service.controller.request.CreateOrderRequest;
import com.poc.order_service.controller.response.OrderResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "orders",
        indexes = {
            @Index( name = "idx_order_id", columnList = "order_id")
        }
)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id", unique = true)
    private String orderId;

    @Column(name = "order_amount")
    private Long orderAmount;

    @Column(name = "merchant_order_reference")
    private String merchantOrderReference;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "pre_auth")
    private boolean praAuth;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    @NotNull
    public static Orders fromRequest(@NotNull CreateOrderRequest orderRequest) {
        Orders order = new Orders();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderAmount(orderRequest.getOrderAmount());
        order.setMerchantOrderReference(orderRequest.getMerchantOrderReference());
        order.setMerchantId(orderRequest.getMerchantId());
        order.setPraAuth(orderRequest.isPraAuth());
        return order;
    }

    @NotNull
    public static OrderResponse toResponse(@NotNull Orders order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderAmount(order.getOrderAmount());
        orderResponse.setMerchantOrderReference(order.getMerchantOrderReference());
        orderResponse.setMerchantId(order.getMerchantId());
        orderResponse.setPraAuth(order.isPraAuth());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());
        return orderResponse;
    }
}
