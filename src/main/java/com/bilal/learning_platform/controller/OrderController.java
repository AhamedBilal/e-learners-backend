package com.bilal.learning_platform.controller;

import com.bilal.learning_platform.dto.CartDto;
import com.bilal.learning_platform.model.*;
import com.bilal.learning_platform.payload.request.OrderRequest;
import com.bilal.learning_platform.payload.response.MessageResponse;
import com.bilal.learning_platform.repository.CartRepository;
import com.bilal.learning_platform.repository.OrderDetailsRepository;
import com.bilal.learning_platform.repository.OrderRepository;
import com.bilal.learning_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderController(UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }


    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest request, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        List<Course> courses = cart.getCourses();
        if (courses.size() > 0) {
            Order order = new Order();
            order.setUser(user);
            Order save = orderRepository.save(order);
            courses.forEach(course -> {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(save);
                orderDetails.setCourse(course);
                orderDetailsRepository.save(orderDetails);
            });
            cart.getCourses().clear();
            Cart save1 = cartRepository.save(cart);
            return ResponseEntity.ok(new CartDto(save1));
        } else {
            return ResponseEntity.badRequest().body("No items");
        }

    }

}
