package com.akazimour.orderservice.repository;

import com.akazimour.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    public Optional<List<OrderItem>>findByCustomerName(@RequestParam String customerName);
}
