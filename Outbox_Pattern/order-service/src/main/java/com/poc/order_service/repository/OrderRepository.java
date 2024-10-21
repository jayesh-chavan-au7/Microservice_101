package com.poc.order_service.repository;

import com.poc.order_service.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByOrderId(String orderId);

    void deleteByOrderId(String orderId);
}
