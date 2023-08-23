package com.akazimour.orderservice.repository;

import com.akazimour.orderservice.entity.ProductSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSummaryRepository extends JpaRepository<ProductSummary,Long> {
}
