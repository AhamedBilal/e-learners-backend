package com.bilal.learning_platform.repository;

import com.bilal.learning_platform.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_Username(String user_username);
}
