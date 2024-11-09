package com.poc.order_history.service.envelopes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "order_event_envelope")
public class OrderEventEnvelope {
    private Long id;

    @JsonProperty("aggregate_id")
    private String aggregateId;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("event_type")
    private EventType eventType;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("merchant_order_reference")
    private String merchantOrderReference;

    @JsonProperty("order_amount")
    private Long orderAmount;

    @JsonProperty("pre_auth")
    private Boolean preAuth;
}
