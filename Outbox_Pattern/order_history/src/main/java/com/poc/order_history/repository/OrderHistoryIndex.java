package com.poc.order_history.repository;

import com.poc.order_history.service.envelopes.OrderEventEnvelope;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderHistoryIndex extends ElasticsearchRepository<OrderEventEnvelope, Long>{
}
