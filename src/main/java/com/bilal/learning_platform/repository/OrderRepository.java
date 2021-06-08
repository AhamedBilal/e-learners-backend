package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
