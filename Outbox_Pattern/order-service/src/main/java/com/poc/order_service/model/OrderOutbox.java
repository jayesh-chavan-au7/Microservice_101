package com.poc.order_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "order_outbox")
public class OrderOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "order_amount")
    private Long orderAmount;

    @Column(name = "merchant_order_reference")
    private String merchantOrderReference;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "pre_auth")
    private boolean praAuth;


    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @NotNull
    public static OrderOutbox createOrderOutbox(String aggregateId, Long orderAmount, String merchantId, String merchantOrderReference) {
        OrderOutbox outbox = new OrderOutbox();
        outbox.setAggregateId(aggregateId);
        outbox.setEventType(EventType.ORDER_CREATED);
        outbox.setOrderAmount(orderAmount);
        outbox.setMerchantId(merchantId);
        outbox.setMerchantOrderReference(merchantOrderReference);
        return outbox;
    }

    @NotNull
    public static OrderOutbox updateOrderOutbox(String aggregateId, Long orderAmount, String merchantId, String merchantOrderReference) {
        OrderOutbox outbox = new OrderOutbox();
        outbox.setAggregateId(aggregateId);
        outbox.setEventType(EventType.ORDER_UPDATED);
        outbox.setOrderAmount(orderAmount);
        outbox.setMerchantId(merchantId);
        outbox.setMerchantOrderReference(merchantOrderReference);
        return outbox;
    }
}
