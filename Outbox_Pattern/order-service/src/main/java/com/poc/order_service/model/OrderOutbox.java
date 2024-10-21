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

    @Column(name = "event_payload", nullable = false, columnDefinition = "TEXT")
    private String eventPayload;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @NotNull
    public static OrderOutbox createOrderOutbox(String aggregateId, String eventPayload) {
        OrderOutbox outbox = new OrderOutbox();
        outbox.setAggregateId(aggregateId);
        outbox.setEventType(EventType.ORDER_CREATED);
        outbox.setEventPayload(eventPayload);
        return outbox;
    }

    @NotNull
    public static OrderOutbox updateOrderOutbox(String aggregateId, String eventPayload) {
        OrderOutbox outbox = new OrderOutbox();
        outbox.setAggregateId(aggregateId);
        outbox.setEventType(EventType.ORDER_UPDATED);
        outbox.setEventPayload(eventPayload);
        return outbox;
    }
}
